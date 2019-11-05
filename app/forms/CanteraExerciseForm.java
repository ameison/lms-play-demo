package forms;

import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;



public class CanteraExerciseForm {

    public Long id;

    @Required
    @MinLength(value = 2)
    public String name;

    @Required
    @MinLength(value = 10)
    public String description;

    @Required
    public String level;

    public String timeLimit;
    public String sourceLimit;

    public CanteraExerciseForm() {}

    public CanteraExerciseForm(Long id) {
        this.id = id;
    }

    public String validate() {
        //saveExercise();
        return null;
    }
/*
    private void saveExercise() {
        Exercise exercise = new Exercise();
        exercise.user = UserResource.find.byId(ApplicationController.getUserIdSession());
        exercise.name = this.name;
        exercise.description = this.description;
        exercise.level = Exercise.Level.valueOf(this.level);
        exercise.timeLimit = this.timeLimit;
        exercise.sourceLimit = this.sourceLimit;
        exercise.save();
    }
*/
}