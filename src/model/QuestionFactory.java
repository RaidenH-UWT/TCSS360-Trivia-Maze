/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Factory for building Question objects.
 * 
 * @author Raiden H
 * @version Spring 2025
 */
public class QuestionFactory {
    /**
     * Build a Question object from the given ID.
     * @param theId int ID to grab from the database
     * @return Question object with the given ID and associated state
     */
    public static Question buildQuestion(final int theId) {
        ResultSet question = DatabaseManager.getQuestionById(theId);
        return sqlRowToQuestion(question);
    }

    /**
      * Gets all questions with the given category.
      * @param theCategory String category to select for
      * @return List<Question> of all questions with the given category
      */
    public static List<Question> getAllQuestionsByCategory(final String theCategory) {
        try {
            ResultSet results = DatabaseManager.getAllQuestionsByCategory(theCategory);

            List<Question> resList = new ArrayList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }

            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
      * Gets all questions with the given categories.
      * @param theCategories String array categories to select for
      * @return List<Question> of all questions with the given categories
      */
    public static List<Question> getAllQuestionsByCategories(final String[] theCategories) {
        List<Question> questions = new ArrayList<Question>();
        for (String category : theCategories) {
            questions.addAll(getAllQuestionsByCategory(category));
        }
        return questions;
    }

    /**
     * Gets a random question by its category
     * @param theCategory String category for the desired question
     * @return random Question object with the given category
     */
    public static Question getRandomQuestionByCategory(final String theCategory) {
        List<Question> questions = getAllQuestionsByCategory(theCategory);
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets a random question by its categories
     * @param theCategories String array categories for the desired question
     * @return random Question object with the given categories
     */
    public static Question getRandomQuestionByCategories(final String[] theCategories) {
        List<Question> questions = getAllQuestionsByCategories(theCategories);
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets all questions stored in the database.
     * @return List<Question> of every question in the database
     */
    public static List<Question> getAllQuestions() {
        try {
            ResultSet results = DatabaseManager.getAllQuestions();
            List<Question> resList = new ArrayList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all questions stored in the database not in the test category.
     * @return List<Question> of every question in the database except the test category
     */
    public static List<Question> getAllRealQuestions() {
        try {
            ResultSet results = DatabaseManager.getAllRealQuestions();
            List<Question> resList = new ArrayList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all test questions stored in the database.
     * @return List<Question> of every test question in the database
     */
    public static List<Question> getTestQuestions() {
        try {
            ResultSet results = DatabaseManager.getTestQuestions();
            List<Question> resList = new ArrayList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all questions with the given type.
     * @param theType QuestionType enum to select for
     * @return List<Question> of all questions with the given type
     */
    public static List<Question> getAllQuestionsByType(final QuestionType theType) {
        try {
            ResultSet results = DatabaseManager.getAllQuestionsByType(theType);
            List<Question> resList = new ArrayList<Question>();
            
            while (results.next()) {
                resList.add(sqlRowToQuestion(results));
            }
            return resList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a random question by its associated QuestionType
     * @param theType a QuestionType for the desired question
     * @return random Question object with the given type
     */
    public static Question getRandomQuestionByType(final QuestionType theType) {
        List<Question> questions = getAllQuestionsByType(theType);
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Gets a random question from the database.
     * @return random Question object from the database
     */
    public static Question getRandomQuestion() {
        List<Question> questions = getAllQuestions();
        Random rand = new Random();

        return questions.get(rand.nextInt(questions.size()));
    }

    /**
     * Parses a String and converts it into a java ArrayList splitting the string on commas.
     * @param theSQL String to parse into a list
     * @return ArrayList<String> of the parsed data
     */
    private static ArrayList<String> sqlStringToList(final String theSQL) {
        return new ArrayList<String>(Arrays.asList(theSQL.split(",")));
    }

    /**
     * Parses a SQL ResultSet and returns the next row as a Question object.
     * @param theResults ResultSet to parse the first row of
     * @return Question object parsed
     */
    private static Question sqlRowToQuestion(final ResultSet theResults) {
        Question result = null;
        try {
            // Build a new question based on the Type column
            switch (theResults.getString("Type")) {
                    case "ShortAnswer":
                        result = new ShortAnswerQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            theResults.getString("Answer"), theResults.getInt("Difficulty"));
                        break;
                    case "TrueFalse":
                        result = new TrueFalseQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            theResults.getString("Answer"), theResults.getInt("Difficulty"));
                        break;
                    case "MultipleChoice":
                        result = new MultipleChoiceQuestion(theResults.getInt("ID"), theResults.getString("Question"), 
                            theResults.getString("Answer"), sqlStringToList(theResults.getString("Options")), theResults.getInt("Difficulty"));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown question type.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
