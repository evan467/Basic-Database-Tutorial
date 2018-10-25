package com.e005.evan.physicsgame;

public class Question {

    public int id;
    public String name;
    public String question;
    public String answer;
    public int difficulty;

    public Question(int i, String n, String q, String a, int d){
        id = i;
        name = n;
        question = q;
        answer = a;
        difficulty = d;

    }

    public Question(Question q){
        id = q.id;
        name = q.name;
        question = q.question;
        answer = q.answer;
        difficulty = q.difficulty;

    }

    @Override
    public String toString(){
        String out = name + ": " + question + ", : " + answer;
        return out;
    }

    public String getAnswer(){
        return this.answer;
    }
}
