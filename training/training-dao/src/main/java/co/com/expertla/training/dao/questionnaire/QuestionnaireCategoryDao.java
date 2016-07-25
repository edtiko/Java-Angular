package co.com.expertla.training.dao.questionnaire;

import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.SePaginator;
import co.com.expertla.training.model.entities.QuestionnaireCategory;
import co.com.expertla.training.model.entities.QuestionnaireQuestion;
import java.util.List;


public interface QuestionnaireCategoryDao {
    
    /**
     * Create questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return the questionnaireCategory created
     * @throws DAOException 
     */
    public QuestionnaireCategory create(QuestionnaireCategory questionnaireCategory) throws DAOException;
	
    /**
     * Modify questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return the questionnaireCategory updated
     * @throws DAOException 
     */
    public QuestionnaireCategory merge(QuestionnaireCategory questionnaireCategory) throws DAOException;
	
    /**
     * Delete questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @throws DAOException 
     */
    public void remove(QuestionnaireCategory questionnaireCategory) throws DAOException;
    
    /**
     * Gets all the records from questionnaireCategory <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param sePaginator
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireCategory> findAll(SePaginator sePaginator) throws DAOException;
	
    /**
     * Gets all the records from questionnaireCategory by its id <br>
     * Creation Info:  <br>
     * date 15/08/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireCategory
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireCategory> findByQuestionnaireCategoryId(QuestionnaireCategory questionnaireCategory) throws DAOException; 
    
    /**
     * Gets all the records from questionnaireCategory by questionnaire id <br>
     * Creation Info:  <br>
     * date 26/08/2015 <br>
     * @author Andres Felipe Lopez Rodriguez
     * Info Update:  <br>
     * date 25/09/2015 <br>
     * @author Angela Ramirez
     * 
     * @param questionnaireQuestion
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireCategory> findByQuestionnaireId(QuestionnaireQuestion questionnaireQuestion) throws DAOException; 
    /**
     * Gets all the records from questionnaireCategory by its id <br>
     * Creation Info:  <br>
     * date 25/09/2015 <br>
     * @author Angela Ramirez
     * @param questionnaireQuestion
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireCategory> findByQuestionnaireParentCategoryId(QuestionnaireQuestion questionnaireQuestion) throws DAOException;
    /**
     * Gets all the children category by a list of category parent ids <br>
     * Creation Info:  <br>
     * date 29/09/2015 <br>
     * @author Angela Ramirez
     * @param categoryIds
     * @return
     * @throws DAOException 
     */
    public List<QuestionnaireCategory> findCategoryChildrenByQuestionnaireCategoryId(List<Integer> categoryIds) throws DAOException;
 
}