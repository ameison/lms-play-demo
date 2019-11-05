package models;

import io.ebean.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="user_progress")
public class UserProgress extends BaseModel {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String INICIADO = "1";
    public static final String TERMINADO = "2";

    @EmbeddedId
    public UserContentProgressKey pk = new UserContentProgressKey();

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    public Content content;

    @Column(name = "content_type")
    public Integer contentType;

    public String state;

    @Column(name = "execute_date")
    public Date executeDate;

    @Column(name = "code_game")
    public String codeGame;

    @Column(name = "quiz_answer_id")
    public Long quizAnswerId;

    public static Finder<UserContentProgressKey, UserProgress> find = new Finder<UserContentProgressKey, UserProgress>(UserProgress.class);

    public static UserProgress getProgressByUserContent(Long userId, Long contentId){
        return find.query().where().eq("user_id", userId).eq("content_id", contentId).findOne();
    }

    public static boolean existProgressByUserContent(Long userId, Long contentId){
        UserProgress p = getProgressByUserContent(userId, contentId);
        if(p!=null){
            if(contentId == p.content.id){
                return true;
            }
        }

        return false;
    }

    public static boolean isClosedContent(Long userId, Long contentId){
        UserProgress p = getProgressByUserContent(userId, contentId);
        if(p!=null){
            if(p.state.equals(UserProgress.TERMINADO)){
                return true;
            }
        }
        return false;
    }


    public static void updateUserContentProgress(Long userId, Long contentId, String codeGame){
        UserProgress usp = find.query().where().eq("user_id", userId).eq("content_id", contentId).findOne();
        usp.codeGame = codeGame;
        usp.update();
    }

    public static UserProgress getUserContentProgress(Long userId, Long contentId){
        return UserProgress.find.query().where().eq("user_id", userId).eq("content_id", contentId).findOne();
    }

    public static UserProgress getLastUserProgress(Long userId){
        List<UserProgress> lista = UserProgress.find.query().where().
                eq("user_id", userId).orderBy("updated_date desc").setMaxRows(1).findList();

        if(lista!=null){
            if(!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return null;
    }

    public static void saveContentVideoProgress(Long contentId,Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        Content content = Content.find.byId(ContentVideo.find.byId(contentId).content.id);

        UserContentProgressKey pk = new UserContentProgressKey(userId, content.id);
        UserProgress userProgress = UserProgress.find.byId(pk);
        if(userProgress==null){
            userProgress = new UserProgress();
            userProgress.pk = pk;
            userProgress.contentType = Content.VIDEO;
            userProgress.state = UserProgress.TERMINADO;
            userProgress.executeDate = new Date();
            userProgress.save();
            awardBadgesUser(Content.VIDEO, Badge.PVIDEO, Badge.SVIDEO, Badge.TVIDEO, Badge.VIDEO,userId);
        }else{
            userProgress.state = UserProgress.TERMINADO;
            userProgress.update();
        }
        awardCompleteBadgesUser(userId);
    }

    public static void saveContentQuizProgress(Long quizId, Long quizAnswer, boolean isCorrect, Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        Content content = Content.find.byId(ContentQuiz.find.byId(quizId).content.id);

        /*Logger.error("//////////////////////////");
        Logger.error("USER ID : " + userId);
        Logger.error("CONTENT ID : " + content.id);
        Logger.error("//////////////////////////");
        */

        UserContentProgressKey pk = new UserContentProgressKey(userId, content.id);
        UserProgress userProgress = UserProgress.find.byId(pk);

        if(userProgress==null){
            userProgress = new UserProgress();
            userProgress.pk = pk;
            userProgress.contentType = Content.QUIZ;
            userProgress.state = (isCorrect==true? UserProgress.TERMINADO: UserProgress.INICIADO);
            userProgress.executeDate = new Date();
            userProgress.quizAnswerId = quizAnswer;
            userProgress.save();
            //awardBadgesUser(Content.QUIZ, Badge.PQUIZ, Badge.SQUIZ, Badge.TQUIZ, Badge.QUIZ,userId);
        }else{

            //ESTA FUNCIONALIDAD SE QUITO PARA QUE SE PUEDA CALIFICAR

//            userProgress.state = (isCorrect==true? UserProgress.TERMINADO: UserProgress.INICIADO);
//            userProgress.executeDate = new Date();
//            userProgress.quizAnswerId = quizAnswer;
//            userProgress.update();


            //awardVerifieBadgesUser(Content.QUIZ, Badge.PQUIZ, Badge.SQUIZ, Badge.TQUIZ, Badge.QUIZ,userId);
        }
        //awardCompleteBadgesUser(userId);
    }

    @Embeddable
    public static class UserContentProgressKey {

        public Long userId;
        public Long contentId;

        public boolean equals(Object object) {
            if(object instanceof UserContentProgressKey){
                UserContentProgressKey key = (UserContentProgressKey) object;
                if(this.userId == key.userId && this.contentId == key.contentId){
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return userId.hashCode() + contentId.hashCode();
        }

        public UserContentProgressKey() {

        }

        public UserContentProgressKey(Long userId, Long contentId) {
            this.userId = userId;
            this.contentId = contentId;
        }
    }

    public static void saveContentCodeProgress(Long userId, Long contentId, String codeGame){
        UserProgress usp = new UserProgress();
        usp.pk.userId = User.find.byId(userId).id;
        usp.pk.contentId= Content.find.byId(contentId).id;
        usp.contentType = Content.find.byId(contentId).typeContent;
        usp.codeGame = codeGame;
        usp.save();
    }

    public static void saveContentGame(Long contentGameId, boolean isCorrect, String codeGame,Long userId ){
        //Long userId = ApplicationController.getUserIdSession();
        Content content = Content.find.byId(ContentGame.find.byId(contentGameId).content.id);

        UserContentProgressKey pk = new UserContentProgressKey(userId, content.id);
        UserProgress userProgress = UserProgress.find.byId(pk);

        if(userProgress==null){
            userProgress = new UserProgress();
            userProgress.pk = pk;
            if(isCorrect){
                userProgress.state = UserProgress.TERMINADO;
            }else{
                userProgress.state = UserProgress.INICIADO;
            }
            userProgress.contentType = content.typeContent;
            userProgress.codeGame = codeGame;
            userProgress.executeDate = new Date();
            userProgress.save();
            awardBadgesUser(Content.EXERCISE, Badge.PEXCERCISE, Badge.SEXCERCISE, Badge.TEXCERCISE, Badge.EXERCISE,userId);
        }else{
            userProgress.codeGame = codeGame;
            userProgress.state = (isCorrect==true? UserProgress.TERMINADO: UserProgress.INICIADO);
            userProgress.executeDate = new Date();
            userProgress.update();
            awardVerifieBadgesUser(Content.EXERCISE, Badge.PEXCERCISE, Badge.SEXCERCISE, Badge.TEXCERCISE, Badge.EXERCISE,userId);
        }
        awardCompleteBadgesUser(userId);
    }

    public static void awardBadgesUser(int ContentType, Long idBadgeP, Long idBadgeS, Long idBadgeT, String badgeType,Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        List<UserProgress> lstUp = UserProgress.find.query().where().eq("content_type", ContentType).eq("state",UserProgress.TERMINADO).findList();

        int indBadgeRegister = 0;

        UserBadge ub = new UserBadge();
        ub.user = User.find.byId(userId);

        switch (lstUp.size()){
            case 1:
                ub.badge = Badge.find.byId(idBadgeP);
                break;
            case 2:
                ub.badge = Badge.find.byId(idBadgeS);
                break;
            case 3:
                ub.badge = Badge.find.byId(idBadgeT);
                break;
        }

        ub.type = badgeType;
        ub.registerDate = new Date();

        List<UserBadge> lstUB = UserBadge.getBadgesUser(userId);

        if(lstUB.size() >= 1){
            for (int i = 0; i < lstUB.size(); i++){
                if(lstUB.get(i).badge.id == ub.badge.id){
                    indBadgeRegister++;
                }
            }
            if(indBadgeRegister == 0){
                ub.save();
            }
        }else{
            if(lstUp.size() != 0){
                ub.save();
            }
        }
    }

    public static void awardVerifieBadgesUser(int ContentType, Long idBadgeP, Long idBadgeS, Long idBadgeT, String badgeType,Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        List<UserProgress> lstUserProgress = UserProgress.find.query().where().eq("content_type", ContentType).eq("state",UserProgress.TERMINADO).findList();

        int tamList = lstUserProgress.size();

        UserBadge ub = new UserBadge();
        ub.user = User.find.byId(userId);

        switch (tamList){
            case Badge.BRONZE:
                ub.badge = Badge.find.byId(idBadgeP);
                break;
            case Badge.SILVER:
                ub.badge = Badge.find.byId(idBadgeS);
                break;
            case Badge.GOLD:
                ub.badge = Badge.find.byId(idBadgeT);
                break;
        }
        ub.type = badgeType;
        ub.registerDate = new Date();

        List<UserBadge> lstUserBadges = UserBadge.getBadgesUser(badgeType,userId);
        if(!lstUserBadges.isEmpty()){
            for (int i = 0; i < lstUserBadges.size(); i++) {
                if(lstUserBadges.get(i).badge.id.longValue() == idBadgeT){
                    if(tamList < Badge.GOLD){
                        Long badgeId = lstUserBadges.get(i).badge.id;
                        UserBadge us =  UserBadge.find.query().where().eq("badge_id", badgeId).findOne();
                        us.delete();
                    }else{
                        if(tamList != 0){
                            int indReg = 0;
                            for (int j = 0; j < lstUserBadges.size(); j++) {
                                if(lstUserBadges.get(i).badge.id == ub.badge.id){
                                    indReg++;
                                }
                            }
                            if(indReg == 0){
                                ub.save();
                            }
                        }
                    }
                }else if(lstUserBadges.get(i).badge.id.longValue() == idBadgeS){
                    if(tamList < Badge.SILVER){
                        Long badgeId = lstUserBadges.get(i).badge.id;
                        UserBadge us =  UserBadge.find.query().where().eq("badge_id", badgeId).findOne();
                        us.delete();
                    }else{
                        if(tamList != 0){
                            int indReg = 0;
                            for (int j = 0; j < lstUserBadges.size(); j++) {
                                if(lstUserBadges.get(i).badge.id == ub.badge.id){
                                    indReg++;
                                }
                            }
                            if(indReg == 0){
                                ub.save();
                            }
                        }
                    }
                }else if(lstUserBadges.get(i).badge.id.longValue() == idBadgeP){
                    if(tamList < Badge.BRONZE){
                        Long badgeId = lstUserBadges.get(i).badge.id;
                        UserBadge us =  UserBadge.find.query().where().eq("badge_id", badgeId).findOne();
                        us.delete();
                    }else{
                        if(tamList != 0){
                            int indReg = 0;

                            for (int j = 0; j < lstUserBadges.size(); j++) {
                                if(lstUserBadges.get(i).badge.id == ub.badge.id){
                                    indReg++;
                                }
                            }
                            if(indReg == 0){
                                ub.save();
                            }
                        }
                    }
                }
            }
        }else{
            if(tamList != 0){
                ub.save();
            }
        }
    }

    public static void awardCompleteBadgesUser(Long userId){
        //Long userId = ApplicationController.getUserIdSession();
        List<UserBadge> lstUserBadge = UserBadge.getBadgesUser(userId);

        UserBadge ub = new UserBadge();
        ub.user = User.find.byId(userId);
        ub.badge = Badge.find.byId(Badge.PMAPA);
        ub.type = Badge.MAP;
        ub.registerDate = new Date();

        int sumBadges = 0;
        int totTypeContent = Content.getContentsBadgesValid();

       // Logger.info("***********NUMERO DE CONTENIDOS*************" + totTypeContent);

        if(!lstUserBadge.isEmpty()){
            for (int i = 0; i < lstUserBadge.size(); i++) {

                String type = lstUserBadge.get(i).type.toString();

                if(type.equals(Badge.VIDEO) || type.equals(Badge.QUIZ) || type.equals(Badge.EXERCISE)){
                    sumBadges++;
                }
            }
            if(sumBadges >= 3){
                ub.save();
            }else if(sumBadges < 3){//EL 3 SE REEMPLAZA POR totTypeContent POR PRUEBAS ESTA 3
                for (int j = 0; j < lstUserBadge.size(); j++) {

                    if(lstUserBadge.get(j).badge.id.longValue() == Badge.PMAPA){
                        Long badgeId = lstUserBadge.get(j).badge.id;
                        UserBadge us =  UserBadge.find.query().where().eq("badge_id", badgeId).findOne();
                        us.delete();
                    }
                }
            }
        }
    }

    //ApplicationController.getUserIdSession
    public static List<UserProgress> getUserQuestionByCourse(Long idCourse, Long userId){
        return UserProgress.find.query().where()
                .eq("content_type", Content.QUIZ)
                .eq("content.lesson.chapter.course.id", idCourse)
                .eq("user_id", userId).findList();
    }

    public static long getUserQuestionCorrectsByCourse(Long idCourse, Long userId){
        int contCorrect = 0;
        List<UserProgress> ups = UserProgress.find.query().where()
                .eq("content_type", Content.QUIZ)
                .eq("content.lesson.chapter.course.id", idCourse)
                .eq("user_id", userId).findList();

        int size = ups.size();

        if (size > 0){

            double val_pregunta = 20 / size;
            for (UserProgress up : ups) {
                if (up.state.equals(UserProgress.TERMINADO)){
                    contCorrect++;
                }
            }
            return Math.round(val_pregunta * contCorrect);

        }else{
            return new Long(0);
        }
    }


}
