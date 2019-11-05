package controllers.admin;

import forms.*;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import util.Files;
import util.Util;
import views.html.student.page404;
import views.html.student.webapp.admin.*;
import play.mvc.Http;
import javax.inject.Inject;
import java.io.File;
import java.util.List;

public class AdminController extends Controller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Inject private FormFactory formFactory;
    
    @Security.Authenticated(Secured.class)
    public Result courses() {
        String userRol = session(User.USER_ROL);
        List<Course> lstCourses = Course.getCoursesbyAdmin();
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(courses.render(lstCourses));
            default:
                return ok(page404.render());
        }
    }

    public Result formRegisterCourse(){
        Form<CourseForm> registerForm = formFactory.form(CourseForm.class);
        return ok(registerCourse.render(registerForm));
    }

    public Result registerCourse(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<CourseForm> registerForm = formFactory.form(CourseForm.class).bindFromRequest();

        if(registerForm.hasErrors()){
            return badRequest(registerCourse.render(registerForm));
        }else {
            CourseForm courseForm = registerForm.get();
            Course course = new Course();
            course.code = courseForm.code;
            course.name = courseForm.name;
            course.description = courseForm.description;
            course.courseFree = courseForm.courseFree;
            course.urlVideo = courseForm.urlVideo;

            try {
                Http.MultipartFormData.FilePart<File> fileImage = formRequestFile.getFile("fileImage");
                if (fileImage != null) {
                    course.urlImage = Files.saveImageFile(fileImage);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            course.save();

            CourseLibrary courseLibrary = new CourseLibrary();
            courseLibrary.price = courseForm.price;
            courseLibrary.course = course;
            courseLibrary.state = CourseLibrary.Status.ACTIVE;
            courseLibrary.save();

            return redirect(controllers.admin.routes.AdminController.courses());
        }
    }

    public Result formEditCourse(String codeCourse){
        Course course = Course.find.query().where().eq("code", codeCourse).findOne();
        if (course != null){
            CourseLibrary courseLibrary = CourseLibrary.find.query().where().eq("course_id", course.id).findOne();
            Form<CourseForm> editForm = formFactory.form(CourseForm.class).fill(new CourseForm(course, courseLibrary));
            return ok(editCourse.render(editForm));
        }else{
            return ok(page404.render());
        }
    }

    public Result editCourse(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<CourseForm> editForm = formFactory.form(CourseForm.class).bindFromRequest();
        if(editForm.hasErrors()){
            return badRequest(editCourse.render(editForm));
        }else {
            CourseForm courseForm = editForm.get();

            Course course = Course.find.byId(courseForm.id);
            course.code = courseForm.code;
            course.name = courseForm.name;
            course.description = courseForm.description;
            course.courseFree = courseForm.courseFree;
            course.urlVideo = courseForm.urlVideo;

            try {
                Http.MultipartFormData.FilePart<File> fileImage = formRequestFile.getFile("fileImage");
                if (fileImage != null) {
                    course.urlImage = Files.saveImageFile(fileImage);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            course.update();

            CourseLibrary courseLibrary = CourseLibrary.find.query().where().eq("course_id", course.id).findOne();
            if (courseLibrary != null){
                courseLibrary.price = courseForm.price;
                courseLibrary.update();
            }

            return redirect(controllers.admin.routes.AdminController.courses());
        }
    }

    public Result deleteCourse(Long idCourse){
        CourseLibrary cl = CourseLibrary.getCourseById(idCourse);
        cl.delete();
        Course course = new Course(idCourse);
        course.delete();
        return redirect(controllers.admin.routes.AdminController.courses());
    }

    @Security.Authenticated(Secured.class)
    public Result chapters() {
        String userRol = session(User.USER_ROL);
        List<Chapter> chaptersList = Chapter.getChapters();
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(chapters.render(chaptersList));
            default:
                return ok(page404.render());
        }
    }

    public Result formRegisterChapter(){
        Form<ChapterForm> registerForm = formFactory.form(ChapterForm.class);
        return ok(registerChapter.render(registerForm));
    }

    public Result registerChapter(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<ChapterForm> registerForm = formFactory.form(ChapterForm.class).bindFromRequest();

        if(registerForm.hasErrors()){
            return badRequest(registerChapter.render(registerForm));
        }else {
            ChapterForm chapterForm = registerForm.get();
            Chapter chapter = new Chapter();
            chapter.name = chapterForm.name;
            chapter.course = new Course(chapterForm.idCourse);

            try {
                Http.MultipartFormData.FilePart<File> fileImage = formRequestFile.getFile("fileImage");
                if (fileImage != null) {
                    chapter.imagen = Files.saveImageFile(fileImage);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            chapter.save();
            return redirect(controllers.admin.routes.AdminController.chapters());
        }
    }

    public Result formEditChapter(Long id){
        Chapter chapter = Chapter.find.byId(id);
        if (chapter != null){
            List<Lesson> lessons = Lesson.getLessonsByChapterId(chapter.id);
            Form<ChapterForm> editForm = formFactory.form(ChapterForm.class).fill(new ChapterForm(chapter));
            return ok(editChapter.render(editForm, lessons));
        }else{
            return ok(page404.render());
        }
    }

    public Result editChapter(){
        logger.debug("Actualizando capitulo");
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<ChapterForm> editForm = formFactory.form(ChapterForm.class).bindFromRequest();
        ChapterForm chapterForm = editForm.get();

        if(editForm.hasErrors()){
            List<Lesson> lessons = Lesson.getLessonsByChapterId(chapterForm.id);
            return badRequest(editChapter.render(editForm, lessons));
        }else {

            Chapter chapter = Chapter.find.byId(chapterForm.id);
            chapter.name = chapterForm.name;
            chapter.course = new Course(chapterForm.idCourse);

            try {
                Http.MultipartFormData.FilePart<File> fileImage = formRequestFile.getFile("fileImage");
                if (fileImage != null) {
                    chapter.imagen = Files.saveImageFile(fileImage);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            chapter.update();
            logger.debug("Actualizado correctamente", chapter.toString());
            return redirect(controllers.admin.routes.AdminController.chapters());
        }
    }

    public Result deleteChapter(Long idCourse){
        Chapter chapter = new Chapter(idCourse);
        chapter.delete();
        return redirect(controllers.admin.routes.AdminController.chapters());
    }

    public Result formRegisterLesson(Long idChapter){
        Form<LessonForm> registerForm = formFactory.form(LessonForm.class);
        return ok(registerLesson.render(registerForm, idChapter));
    }

    public Result registerLesson(){
        Form<LessonForm> registerForm = formFactory.form(LessonForm.class).bindFromRequest();
        LessonForm lessonForm = registerForm.get();
        if(registerForm.hasErrors()){
            return badRequest(registerLesson.render(registerForm, lessonForm.idChapter));
        }else {
            Lesson lesson = new Lesson();
            lesson.name = lessonForm.name;
            lesson.orderLesson = lessonForm.orderLesson;
            lesson.chapter = new Chapter(lessonForm.idChapter);
            lesson.save();
            return redirect(controllers.admin.routes.AdminController.formEditChapter(lessonForm.idChapter));
        }
    }

    public Result formEditLesson(Long id){
        Lesson lesson = Lesson.find.byId(id);
        if (lesson != null){
            List<Content> contents = Content.getAllContentXIdLessson(lesson.id);
            Form<LessonForm> editForm = formFactory.form(LessonForm.class).fill(new LessonForm(lesson));
            return ok(editLesson.render(editForm, contents));
        }else{
            return ok(page404.render());
        }
    }

    public Result editLesson(){
        logger.debug("Actualizando capitulo");
        Form<LessonForm> editForm = formFactory.form(LessonForm.class).bindFromRequest();

        LessonForm lessonForm = editForm.get();

        List<Content> contents = Content.getAllContentXIdLessson(lessonForm.id);

        if(editForm.hasErrors()){
            return badRequest(editLesson.render(editForm, contents));
        } else {
            Lesson lesson = Lesson.find.byId(lessonForm.id);
            lesson.id = lessonForm.id;
            lesson.name = lessonForm.name;
            lesson.orderLesson= lessonForm.orderLesson;
            lesson.update();
            logger.debug("Actualizado correctamente", lesson.toString());
            return redirect(controllers.admin.routes.AdminController.formEditChapter(lesson.chapter.id));
        }
    }

    public Result deleteLesson(Long idLesson){
        Lesson lesson = Lesson.find.byId(idLesson);
        Long chapterId = lesson.chapter.id;
        lesson.delete();
        return redirect(controllers.admin.routes.AdminController.formEditChapter(chapterId));
    }

    //REGISTRO DE CONTENIDOS

    public Result formRegisterContent(int typeContent, Long idLesson){

        if (typeContent == Content.VIDEO){
            Form<ContentVideoForm> registerForm = formFactory.form(ContentVideoForm.class);
            return ok(registerContentVideo.render(registerForm, idLesson));
        } else if (typeContent == Content.QUIZ){
            Form<ContentAdminQuizForm> registerForm = formFactory.form(ContentAdminQuizForm.class);
            return ok(registerContentQuiz.render(registerForm, idLesson));
        } else {
            Form<ContentDownloadForm> registerForm = formFactory.form(ContentDownloadForm.class);
            return ok(registerContentDownload.render(registerForm, idLesson));
        }
    }

    public Result formEditContent(Long id){
        Content content = Content.find.byId(id);
        if (content != null){
            if (content.typeContent == Content.VIDEO) {
                ContentVideo contentVideo = ContentVideo.getvidebyidContent(content.id);
                if (contentVideo != null){
                    Form<ContentVideoForm> editForm = formFactory.form(ContentVideoForm.class).fill(new ContentVideoForm(contentVideo));
                    return ok(editContentVideo.render(editForm));
                }else{
                    return ok(page404.render());
                }
            }else if (content.typeContent == Content.DOWNLOAD) {
                ContentDownload contentDownload = ContentDownload.getDownloadbyidContent(content.id);
                if (contentDownload != null){
                    Form<ContentDownloadForm> editForm = formFactory.form(ContentDownloadForm.class).fill(new ContentDownloadForm(contentDownload));
                    return ok(editContentDownload.render(editForm));
                }else{
                    return ok(page404.render());
                }
            }else if (content.typeContent == Content.QUIZ){
                ContentQuiz contentQuiz = ContentQuiz.getQuizByContentId(content.id);
                if (contentQuiz != null){
                    List<ContentQuizAnswer> cqa = ContentQuizAnswer.getAnswersByContentQuiz(contentQuiz.id);
                    Form<ContentAdminQuizForm> editForm = formFactory.form(ContentAdminQuizForm.class).fill(new ContentAdminQuizForm(contentQuiz));
                    return ok(editContentQuiz.render(editForm, cqa));
                }else{
                    return ok(page404.render());
                }
            }else{
                return ok(page404.render());
            }
        }else{
            return ok(page404.render());
        }
    }

    public Result registerContentVideo(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        Form<ContentVideoForm> registerForm = formFactory.form(ContentVideoForm.class).bindFromRequest();
        ContentVideoForm contentVideoForm = registerForm.get();
        if(registerForm.hasErrors()){
            return badRequest(registerContentVideo.render(registerForm, contentVideoForm.idLesson));
        } else {

            //DATOS DEL CONTENIDO
            Content content = new Content();
            content.name = contentVideoForm.contentName;
            content.typeContent = Content.VIDEO;
            content.orderContent = contentVideoForm.orderContent;
            content.lesson = new Lesson(contentVideoForm.idLesson);
            content.save();

            //DATOS DEL CONTENIDO TIPO VIDEO
            ContentVideo contentVideo = new ContentVideo();
            contentVideo.title = contentVideoForm.title;
            contentVideo.description = contentVideoForm.description;

            try {
                Http.MultipartFormData.FilePart<File> fileDownload = formRequestFile.getFile("fileVideo");

                if (fileDownload != null) {
                    contentVideo.url_video = Files.saveFile(fileDownload);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
//            contentVideo.url_video = contentVideoForm.urlVideo;

            contentVideo.time = "0";
            contentVideo.tocken = "";
            contentVideo.isolation = ContentVideo.Isolation.OPEN;
            contentVideo.video_host = ContentVideo.VideoHost.OTROS;
            contentVideo.content = content;
            contentVideo.save();

            return redirect(controllers.admin.routes.AdminController.formEditLesson(contentVideoForm.idLesson));
        }
    }

    public Result editContentVideo(){
        logger.debug("Actualizando contenido video");
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        Form<ContentVideoForm> editForm = formFactory.form(ContentVideoForm.class).bindFromRequest();
        ContentVideoForm contentVideoForm = editForm.get();

        if(editForm.hasErrors()){
            return badRequest(editContentVideo.render(editForm));
        } else {

            Content content = Content.find.byId(contentVideoForm.idContent);
            content.name = contentVideoForm.contentName;
            content.orderContent = contentVideoForm.orderContent;
            content.update();

            ContentVideo contentVideo = ContentVideo.find.byId(contentVideoForm.id);
            contentVideo.title = contentVideoForm.title;
            contentVideo.description = contentVideoForm.description;

            try {
                Http.MultipartFormData.FilePart<File> fileDownload = formRequestFile.getFile("fileVideo");

                if (fileDownload != null) {
                    contentVideo.url_video = Files.saveFile(fileDownload);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            //contentVideo.url_video = contentVideoForm.urlVideo;
            contentVideo.update();

            return redirect(controllers.admin.routes.AdminController.formEditLesson(content.lesson.id));
        }
    }

    public Result registerContentDownload(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        Form<ContentDownloadForm> registerForm = formFactory.form(ContentDownloadForm.class).bindFromRequest();
        ContentDownloadForm contentDownloadForm = registerForm.get();
        if(registerForm.hasErrors()){
            return badRequest(registerContentDownload.render(registerForm, contentDownloadForm.idLesson));
        } else {

            //DATOS DEL CONTENIDO
            Content content = new Content();
            content.name = contentDownloadForm.contentName;
            content.typeContent = Content.DOWNLOAD;
            content.orderContent = contentDownloadForm.orderContent;
            content.lesson = new Lesson(contentDownloadForm.idLesson);
            content.save();

            //DATOS DEL CONTENIDO TIPO VIDEO
            ContentDownload contentDownload = new ContentDownload();
            contentDownload.title = contentDownloadForm.title;
            contentDownload.description = contentDownloadForm.description;
            contentDownload.content = content;

            try {
                Http.MultipartFormData.FilePart<File> fileDownload = formRequestFile.getFile("fileDownload");

                if (fileDownload != null) {
                    contentDownload.urlFile = Files.saveFile(fileDownload);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            contentDownload.save();

            return redirect(controllers.admin.routes.AdminController.formEditLesson(contentDownloadForm.idLesson));

        }
    }

    public Result editContentDownload(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        logger.debug("Actualizando contenido descarga");
        Form<ContentDownloadForm> editForm = formFactory.form(ContentDownloadForm.class).bindFromRequest();
        ContentDownloadForm contentDownloadForm = editForm.get();

        if(editForm.hasErrors()){
            return badRequest(editContentDownload.render(editForm));
        } else {

            Content content = Content.find.byId(contentDownloadForm.idContent);
            content.name = contentDownloadForm.contentName;
            content.orderContent = contentDownloadForm.orderContent;
            content.update();

            ContentDownload contentDownload = ContentDownload.find.byId(contentDownloadForm.id);
            contentDownload.title = contentDownloadForm.title;
            contentDownload.description = contentDownloadForm.description;

            try{
                Http.MultipartFormData.FilePart<File> fileDownload = formRequestFile.getFile("fileDownload");

                if (fileDownload != null) {
                    contentDownload.urlFile = Files.saveFile(fileDownload);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            contentDownload.update();

            return redirect(controllers.admin.routes.AdminController.formEditLesson(content.lesson.id));
        }
    }

    public Result registerContentQuiz(){

        Form<ContentAdminQuizForm> registerForm = formFactory.form(ContentAdminQuizForm.class).bindFromRequest();
        ContentAdminQuizForm contentAdminQuizForm = registerForm.get();
        if(registerForm.hasErrors()){
            return badRequest(registerContentQuiz.render(registerForm, contentAdminQuizForm.idLesson));
        } else {
            //DATOS DEL CONTENIDO
            Content content = new Content();
            content.name = contentAdminQuizForm.contentName;
            content.typeContent = Content.QUIZ;
            content.orderContent = contentAdminQuizForm.orderContent;
            content.lesson = new Lesson(contentAdminQuizForm.idLesson);
            content.save();

            //DATOS DEL CONTENIDO TIPO PREGUNTA
            ContentQuiz contentQuiz = new ContentQuiz();
            contentQuiz.question = contentAdminQuizForm.question;
            contentQuiz.content = content;
            contentQuiz.save();

            //PASAR AL REGISTRO DE RESPUESTAS
            return redirect(controllers.admin.routes.AdminController.formEditLesson(content.lesson.id));
        }
    }

    public Result editContentQuiz(){
        logger.debug("Actualizando contenido descarga");
        Form<ContentAdminQuizForm> editForm = formFactory.form(ContentAdminQuizForm.class).bindFromRequest();
        ContentAdminQuizForm contentAdminQuizForm = editForm.get();
        List<ContentQuizAnswer> cqa = ContentQuizAnswer.getAnswersByContentQuiz(contentAdminQuizForm.id);

        if(editForm.hasErrors()){
            return badRequest(editContentQuiz.render(editForm, cqa));
        } else {

            Content content = Content.find.byId(contentAdminQuizForm.idContent);
            content.name = contentAdminQuizForm.contentName;
            content.orderContent = contentAdminQuizForm.orderContent;
            content.update();

            ContentQuiz contentQuiz = ContentQuiz.find.byId(contentAdminQuizForm.id);
            contentQuiz.question = contentAdminQuizForm.question;
            contentQuiz.update();

            return redirect(controllers.admin.routes.AdminController.formEditLesson(content.lesson.id));
        }
    }

    public Result formRegisterContentAnswer(Long idContentQuiz){
        ContentQuiz cq = ContentQuiz.find.byId(idContentQuiz);
        Form<ContentAdminQuizAnswerForm> registerForm = formFactory.form(ContentAdminQuizAnswerForm.class);
        return ok(registerContentQuizAnswer.render(registerForm, idContentQuiz, cq.content.id));
    }

    public Result registerContentQuizAnswer(){

        Form<ContentAdminQuizAnswerForm> registerForm = formFactory.form(ContentAdminQuizAnswerForm.class).bindFromRequest();
        ContentAdminQuizAnswerForm contentAdminQuizAnswerForm = registerForm.get();

        ContentQuiz cq = ContentQuiz.find.byId(contentAdminQuizAnswerForm.idContentQuiz);

        if(registerForm.hasErrors()){
            return badRequest(registerContentQuizAnswer.render(registerForm, contentAdminQuizAnswerForm.idContentQuiz, cq.content.id));
        } else {
            //DATOS DEL CONTENIDO TIPO PREGUNTA
            ContentQuizAnswer quizAnswer = new ContentQuizAnswer();
            quizAnswer.answer = contentAdminQuizAnswerForm.answer;
            quizAnswer.contentQuiz = new ContentQuiz(contentAdminQuizAnswerForm.idContentQuiz);
            quizAnswer.orderAnswer = contentAdminQuizAnswerForm.orderAnswer;
            quizAnswer.isCorrectAnswer = contentAdminQuizAnswerForm.isCorrectAnswer;
            quizAnswer.save();

            return redirect(controllers.admin.routes.AdminController.formEditContent(cq.content.id));

        }
    }

    public Result formEditContentAnswer(Long idAnswer){
        ContentQuizAnswer cqa = ContentQuizAnswer.find.byId(idAnswer);
        Form<ContentAdminQuizAnswerForm> editForm = formFactory.form(ContentAdminQuizAnswerForm.class).fill(new ContentAdminQuizAnswerForm(cqa));
        return ok(editContentQuizAnswer.render(editForm, cqa.contentQuiz.content.id));
    }

    public Result editContentQuizAnswer(){
        logger.debug("Actualizando contenido respuesta pregunta");
        Form<ContentAdminQuizAnswerForm> editForm = formFactory.form(ContentAdminQuizAnswerForm.class).bindFromRequest();
        ContentAdminQuizAnswerForm contentAdminQuizAnswerForm = editForm.get();

        ContentQuiz cq = ContentQuiz.find.byId(contentAdminQuizAnswerForm.idContentQuiz);

        if(editForm.hasErrors()){
            return badRequest(editContentQuizAnswer.render(editForm, cq.content.id));
        } else {

            ContentQuizAnswer quizAnswer = new ContentQuizAnswer();
            quizAnswer.id = contentAdminQuizAnswerForm.id;
            quizAnswer.answer = contentAdminQuizAnswerForm.answer;
            quizAnswer.contentQuiz = new ContentQuiz(contentAdminQuizAnswerForm.idContentQuiz);
            quizAnswer.orderAnswer = contentAdminQuizAnswerForm.orderAnswer;
            quizAnswer.isCorrectAnswer = contentAdminQuizAnswerForm.isCorrectAnswer;
            quizAnswer.update();

            return redirect(controllers.admin.routes.AdminController.formEditContent(cq.content.id));
        }
    }

    /*REGISTRO DE EVENTOS*/

    @Security.Authenticated(Secured.class)
    public Result events() {
        String userRol = session(User.USER_ROL);
        List<Event> lstEvents = Event.find.all();
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(events.render(lstEvents));
            default:
                return ok(page404.render());
        }
    }

    public Result formRegisterEvent(){
        Form<EventForm> registerForm = formFactory.form(EventForm.class);
        return ok(registerEvent.render(registerForm));
    }

    public Result registerEvent(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<EventForm> registerForm = formFactory.form(EventForm.class).bindFromRequest();
        if(registerForm.hasErrors()){
            return badRequest(registerEvent.render(registerForm));
        }else {
            EventForm eventForm = registerForm.get();
            Event event = new Event();
            event.name = eventForm.name;
            event.description = eventForm.description;
            event.address = eventForm.address;
            event.startDate = Util.formatDateTime(eventForm.startDate);
            event.endDate = Util.formatDateTime(eventForm.endDate);

            try {
                Http.MultipartFormData.FilePart<File> picture = formRequestFile.getFile("fileImage");

                if (picture != null) {
                    event.urlImage = Files.saveImageFile(picture);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            event.save();
            return redirect(controllers.admin.routes.AdminController.events());
        }
    }

    public Result formEditEvent(Long id){
        Event event = Event.find.byId(id);
        if (event != null){
            Form<EventForm> editForm = formFactory.form(EventForm.class).fill(new EventForm(event));
            return ok(editEvent.render(editForm));
        }else{
            return ok(page404.render());
        }
    }

    public Result editEvent(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();
        Form<EventForm> editForm = formFactory.form(EventForm.class).bindFromRequest();
        EventForm eventForm = editForm.get();

        if(editForm.hasErrors()){
            return badRequest(editEvent.render(editForm));
        }else {
            Event event = Event.find.byId(eventForm.id);
            event.id = eventForm.id;
            event.name = eventForm.name;
            event.description = eventForm.description;
            event.address = eventForm.address;
            event.startDate = Util.formatDateTime(eventForm.startDate);
            event.endDate = Util.formatDateTime(eventForm.endDate);

            try {
                Http.MultipartFormData.FilePart<File> picture = formRequestFile.getFile("fileImage");

                if (picture != null) {
                    String newImage = Files.saveImageFile(picture);
                    if (newImage != null){
                        event.urlImage = newImage;
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            event.update();
            return redirect(controllers.admin.routes.AdminController.events());
        }
    }

    public Result deleteEvent(Long idEvent){
        Event event = new Event(idEvent);
        event.delete();
        return redirect(controllers.admin.routes.AdminController.events());
    }

    /*BANNERS*/

    @Security.Authenticated(Secured.class)
    public Result banners() {
        String userRol = session(User.USER_ROL);
        List<Banner> lstBanners = Banner.find.all();
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(banners.render(lstBanners));
            default:
                return ok(page404.render());
        }
    }

    public Result formRegisterBanner(){
        Form<BannerForm> registerForm = formFactory.form(BannerForm.class);
        return ok(registerBanner.render(registerForm));
    }

    public Result registerBanner(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        Form<BannerForm> registerForm = formFactory.form(BannerForm.class).bindFromRequest();
        logger.info("INGRESO :::: ");
        if(registerForm.hasErrors()){
            logger.info("ERRORRRRRRRR");
            return badRequest(registerBanner.render(registerForm));
        }else {
            logger.info("PASO TRANQUILO");
            BannerForm bannerForm = registerForm.get();
            Banner banner = new Banner();
            banner.name = bannerForm.name;
            banner.description = bannerForm.description;

            try {
                Http.MultipartFormData.FilePart<File> picture = formRequestFile.getFile("imageBanner");

                if (picture != null) {
                    banner.urlImage = Files.saveImageFile(picture);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            banner.save();
            return redirect(controllers.admin.routes.AdminController.banners());
        }
    }

    public Result formEditBanner(Long id){
        Banner banner = Banner.find.byId(id);
        if (banner != null){
            Form<BannerForm> editForm = formFactory.form(BannerForm.class).fill(new BannerForm(banner));
            return ok(editBanner.render(editForm));
        }else{
            return ok(page404.render());
        }
    }

    public Result editBanner(){
        Http.MultipartFormData<File> formRequestFile = request().body().asMultipartFormData();

        Form<BannerForm> editForm = formFactory.form(BannerForm.class).bindFromRequest();
        BannerForm bannerForm = editForm.get();

        if(editForm.hasErrors()){
            return badRequest(editBanner.render(editForm));
        }else {
            Banner banner = Banner.find.byId(bannerForm.id);
            banner.id = bannerForm.id;
            banner.name = bannerForm.name;
            banner.description = bannerForm.description;

            try {
                Http.MultipartFormData.FilePart<File> picture = formRequestFile.getFile("imageBanner");

                if (picture != null) {
                    banner.urlImage = Files.saveImageFile(picture);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }

            banner.update();
            logger.debug("Actualizado correctamente", banner.toString());
            return redirect(controllers.admin.routes.AdminController.banners());
        }
    }

    public Result deleteBanner(Long idBanner){
        Banner banner = new Banner(idBanner);
        banner.delete();
        return redirect(controllers.admin.routes.AdminController.banners());
    }

    /*VIDEO*/
    @Security.Authenticated(Secured.class)
    public Result videos() {
        String userRol = session(User.USER_ROL);
        List<Video> lstVideos = Video.find.all();
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(videos.render(lstVideos));
            default:
                return ok(page404.render());
        }
    }

    public Result formRegisterVideo(){
        Form<VideoForm> registerForm = formFactory.form(VideoForm.class);
        return ok(registerVideo.render(registerForm));
    }

    public Result registerVideo(){
        Form<VideoForm> registerForm = formFactory.form(VideoForm.class).bindFromRequest();
        if(registerForm.hasErrors()){
            logger.info("ERRORRRRRRRR");
            return badRequest(registerVideo.render(registerForm));
        }else {
            logger.info("PASO TRANQUILO");
            VideoForm videoForm = registerForm.get();
            Video video = new Video();
            video.name = videoForm.name;
            video.description = videoForm.description;
            video.urlVideo = videoForm.urlVideo;
            video.save();
            return redirect(controllers.admin.routes.AdminController.videos());
        }
    }

    public Result formEditVideo(Long id){
        Video video = Video.find.byId(id);
        if (video != null){
            Form<VideoForm> editForm = formFactory.form(VideoForm.class).fill(new VideoForm(video));
            return ok(editVideo.render(editForm));
        }else{
            return ok(page404.render());
        }
    }

    public Result editVideo(){
        Form<VideoForm> editForm = formFactory.form(VideoForm.class).bindFromRequest();
        VideoForm videoForm = editForm.get();

        if(editForm.hasErrors()){
            return badRequest(editVideo.render(editForm));
        }else {
            Video video = Video.find.byId(videoForm.id);
            video.id = videoForm.id;
            video.name = videoForm.name;
            video.description = videoForm.description;
            video.urlVideo = videoForm.urlVideo;
            video.update();
            return redirect(controllers.admin.routes.AdminController.videos());
        }
    }

    public Result deleteVideo(Long idVideo){
        Video video = new Video(idVideo);
        video.delete();
        return redirect(controllers.admin.routes.AdminController.videos());
    }

    @Security.Authenticated(Secured.class)
    public Result users() {
        String userRol = session(User.USER_ROL);

        List<User> lstUsers = User.find.query().where().eq("rol", "S").findList();

        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(users.render(lstUsers));
            default:
                return ok(page404.render());
        }
    }

    @Security.Authenticated(Secured.class)
    public Result courseByUser(Long idUser) {
        String userRol = session(User.USER_ROL);
        List<UserCourse> userCourses =  UserCourse.getMySchoolCourses(idUser);
        switch(User.Rol.valueOf(userRol)){
            case ADMINISTRADOR:
                return ok(userCalification.render(userCourses));
            default:
                return ok(page404.render());
        }
    }


}