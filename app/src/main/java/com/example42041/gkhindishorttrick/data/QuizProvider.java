package com.example42041.gkhindishorttrick.data;

import com.example42041.gkhindishorttrick.model.Quiz;

import java.util.Arrays;
import java.util.List;

public class QuizProvider {
    public List<Quiz> readQuizzes() {
        return Arrays.asList(new Quiz[]{new Quiz("spider, boy walking"), new Quiz("spider, boy walking"), new Quiz("star, gun, bomb"), new Quiz("half moon, boy and girl in love, wolf"), new Quiz("drumstick, rib, game controller"), new Quiz("bride, boy, pow, pow"), new Quiz("cat, boot")});
    }
}
