package forms;

import models.ContentQuizAnswer;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

public class ContentAdminQuizAnswerForm {

    public Long id;

    @Required
    @MinLength(value = 4)
    public String answer;

    public int orderAnswer;

    public boolean isCorrectAnswer;

    //DATOS DEL CONTENIDO TIPO RESPUESTA

    public Long idContentQuiz;

    public String validate() {
//        if (existAnsweCorrect()){
//            return "Ya has seleccionado uan respuesta como correcta.";
//        }
        return null;
    }

    private boolean existAnsweCorrect(){
        ContentQuizAnswer cqa = ContentQuizAnswer.find.query().where()
                .eq("content_quiz_id", this.idContentQuiz)
                .eq("is_correct_answer", true).findOne();
        if (cqa != null)
            return true;
        return false;
    }

    public ContentAdminQuizAnswerForm(ContentQuizAnswer quizAnswer){
        this.id = quizAnswer.id;
        this.answer = quizAnswer.answer;
        this.orderAnswer = quizAnswer.orderAnswer;
        this.isCorrectAnswer = quizAnswer.isCorrectAnswer;
        if (quizAnswer.contentQuiz != null) {
            this.idContentQuiz = quizAnswer.contentQuiz.id;
        }
    }

    public ContentAdminQuizAnswerForm(){}

}