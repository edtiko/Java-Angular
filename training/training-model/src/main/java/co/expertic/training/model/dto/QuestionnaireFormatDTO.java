package co.expertic.training.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * It's used to obtain all the data needed for a questionnaire and its categories, questions, question responses
 * and question options
 * Creation Info:
 * date 17/09/2015 
 * @author Angela Ramirez 
 */
@Entity
public class QuestionnaireFormatDTO implements Serializable {
    private int indAdditional;
    @Id
    private Integer questionnaireFormatDtoId;
    private Integer questionnaireId;
    private Integer providerId;
    private Integer questionOptionId;
    private Integer oQuestionId;
    private Integer questionnaireQuestionId;
    private Integer qqQuestionId;
    private Integer qqQuestionnaireCategoryId;
    private Integer questionOrder;
    private Integer questionnaireCategoryId;
    private Integer questionnaireParentId;
    private Integer questionId;
    private Integer dataTypeId;
    private Integer questionStatus;
    private Integer questionnaireResponseId;
    private Integer rQuestionnaireQuestionId;
    private Integer rQuestionOptionId;
    private Integer seUserId;
    private String questionnaireName;
    private String questionnaireCategoryName;
    private String questionnaireCategoryDesc;
    private String questionName;
    private String questionDesc;
    private String unit;
    private String response;
    private String questionOptionName;
    private String questionType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionnaireDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionnaireResponseDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionnaireCategoryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionnaireQuestionDate;

    public int getIndAdditional() {
        return indAdditional;
    }

    public void setIndAdditional(int indAdditional) {
        this.indAdditional = indAdditional;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }



    public Integer getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
    }

    public Integer getoQuestionId() {
        return oQuestionId;
    }

    public void setoQuestionId(Integer oQuestionId) {
        this.oQuestionId = oQuestionId;
    }



    public Integer getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Integer questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public Integer getQqQuestionId() {
        return qqQuestionId;
    }

    public void setQqQuestionId(Integer qqQuestionId) {
        this.qqQuestionId = qqQuestionId;
    }

    public Integer getQqQuestionnaireCategoryId() {
        return qqQuestionnaireCategoryId;
    }

    public void setQqQuestionnaireCategoryId(Integer qqQuestionnaireCategoryId) {
        this.qqQuestionnaireCategoryId = qqQuestionnaireCategoryId;
    }



    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Integer getQuestionnaireCategoryId() {
        return questionnaireCategoryId;
    }

    public void setQuestionnaireCategoryId(Integer questionnaireCategoryId) {
        this.questionnaireCategoryId = questionnaireCategoryId;
    }

    public Integer getQuestionnaireParentId() {
        return questionnaireParentId;
    }

    public void setQuestionnaireParentId(Integer questionnaireParentId) {
        this.questionnaireParentId = questionnaireParentId;
    }


    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    public Integer getQuestionnaireResponseId() {
        return questionnaireResponseId;
    }

    public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
        this.questionnaireResponseId = questionnaireResponseId;
    }

    public Integer getrQuestionnaireQuestionId() {
        return rQuestionnaireQuestionId;
    }

    public void setrQuestionnaireQuestionId(Integer rQuestionnaireQuestionId) {
        this.rQuestionnaireQuestionId = rQuestionnaireQuestionId;
    }

    public Integer getrQuestionOptionId() {
        return rQuestionOptionId;
    }

    public void setrQuestionOptionId(Integer rQuestionOptionId) {
        this.rQuestionOptionId = rQuestionOptionId;
    }

    public Integer getSeUserId() {
        return seUserId;
    }

    public void setSeUserId(Integer seUserId) {
        this.seUserId = seUserId;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public String getQuestionnaireCategoryName() {
        return questionnaireCategoryName;
    }

    public void setQuestionnaireCategoryName(String questionnaireCategoryName) {
        this.questionnaireCategoryName = questionnaireCategoryName;
    }

    public String getQuestionnaireCategoryDesc() {
        return questionnaireCategoryDesc;
    }

    public void setQuestionnaireCategoryDesc(String questionnaireCategoryDesc) {
        this.questionnaireCategoryDesc = questionnaireCategoryDesc;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getQuestionOptionName() {
        return questionOptionName;
    }

    public void setQuestionOptionName(String questionOptionName) {
        this.questionOptionName = questionOptionName;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Date getQuestionnaireDate() {
        return questionnaireDate;
    }

    public void setQuestionnaireDate(Date questionnaireDate) {
        this.questionnaireDate = questionnaireDate;
    }

    public Date getQuestionnaireResponseDate() {
        return questionnaireResponseDate;
    }

    public void setQuestionnaireResponseDate(Date questionnaireResponseDate) {
        this.questionnaireResponseDate = questionnaireResponseDate;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public Date getQuestionnaireCategoryDate() {
        return questionnaireCategoryDate;
    }

    public void setQuestionnaireCategoryDate(Date questionnaireCategoryDate) {
        this.questionnaireCategoryDate = questionnaireCategoryDate;
    }

    public Date getQuestionnaireQuestionDate() {
        return questionnaireQuestionDate;
    }

    public void setQuestionnaireQuestionDate(Date questionnaireQuestionDate) {
        this.questionnaireQuestionDate = questionnaireQuestionDate;
    }

    public Integer getQuestionnaireFormatDtoId() {
        return questionnaireFormatDtoId;
    }

    public void setQuestionnaireFormatDtoId(Integer questionnaireFormatDtoId) {
        this.questionnaireFormatDtoId = questionnaireFormatDtoId;
    }

}
