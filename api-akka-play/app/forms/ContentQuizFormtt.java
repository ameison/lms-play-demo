package forms;


import models.ContentQuizAnswer;
import models.UserProgress;
import play.i18n.Lang;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import javax.inject.Inject;

public class ContentQuizFormtt {
    private MessagesApi messagesApi;

    @Inject
    public ContentQuizFormtt(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    public Long quizId;
    public Long quizAnswer;
    public Long userId;

    public String validate(){

        boolean isCorrect = false;
        if(quizAnswer == null){
            return messagesApi.get(Lang.forCode("es"), "quiz.msj1");
        }

        if(!isCorrectAnswer()){
            UserProgress.saveContentQuizProgress(quizId, quizAnswer, isCorrect,userId);
            return messagesApi.get(Lang.forCode("es"), "quiz.msj2");
        }else{
            isCorrect = true;
        }

        UserProgress.saveContentQuizProgress(quizId, quizAnswer, isCorrect,userId);

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
