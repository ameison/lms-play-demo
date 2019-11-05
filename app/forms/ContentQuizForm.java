package forms;

import models.*;
import play.i18n.Lang;
import play.i18n.MessagesApi;

import javax.inject.Inject;

public class ContentQuizForm {

    public Long quizId;
    public Long quizAnswer;
    public Long userId;

    @Inject
    private MessagesApi messagesApi;



    public String validate(){

        boolean isCorrect = false;
        if(quizAnswer == null){
            return messagesApi.get(Lang.forCode("es"), "quiz.msj1");
        }

        if(!isCorrectAnswer()){
            UserProgress.saveContentQuizProgress(quizId, quizAnswer, isCorrect, userId);
            return messagesApi.get(Lang.forCode("es"), "quiz.msj2");
        }else{
            isCorrect = true;
        }

        UserProgress.saveContentQuizProgress(quizId, quizAnswer, isCorrect, userId);

        return null;
    }


    public boolean isCorrectAnswer(){
        ContentQuizAnswer correctAnswer = ContentQuizAnswer.find.byId(quizAnswer);
        if(correctAnswer.isCorrectAnswer){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString() {
        try {
            return "ContentQuizForm{" +
                    "quizId='" + quizId + '\'' +
                    ", quizAnswer='" + quizAnswer + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
        catch(Exception ex){
            return ex.getMessage();
        }
    }


}
