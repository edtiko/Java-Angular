package co.com.expertla.training.model.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * It's used to bring the information only for the questions by categoryId, questionnaireId and userId
 * with its options and response
 * Creation Info:
 * date 30/09/2015 
 * @author Angela Ramirez 
 */
@Entity
public class QuestionnaireQuestionFormatDto implements Serializable {
    @Id
    private Integer questionnaireQuestionFormatDtoId;
    private Integer questionId;
    private Integer dataTypeId;
    private Integer questionStatus;
    private String questionName;
    private String questionDesc;
    private String unit;
    private int indAdditional;
    private String questionType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionDate;
    private Integer questionnaireQuestionId;
    private Integer questionOrder;
    @Temporal(TemporalType.TIMESTAMP)
    private Date questionnaireQuestionDate;
    private Integer questionOptionId;
    private Short stateId;
    private String questionOptionName;
    private String questionOptionlabel;

    public Integer getQuestionnaireQuestionFormatDtoId() {
        return questionnaireQuestionFormatDtoId;
    }

    public void setQuestionnaireQuestionFormatDtoId(Integer questionnaireQuestionFormatDtoId) {
        this.questionnaireQuestionFormatDtoId = questionnaireQuestionFormatDtoId;
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

    public int getIndAdditional() {
        return indAdditional;
    }

    public void setIndAdditional(int indAdditional) {
        this.indAdditional = indAdditional;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public Integer getQuestionnaireQuestionId() {
        return questionnaireQuestionId;
    }

    public void setQuestionnaireQuestionId(Integer questionnaireQuestionId) {
        this.questionnaireQuestionId = questionnaireQuestionId;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Date getQuestionnaireQuestionDate() {
        return questionnaireQuestionDate;
    }

    public void setQuestionnaireQuestionDate(Date questionnaireQuestionDate) {
        this.questionnaireQuestionDate = questionnaireQuestionDate;
    }

    public Integer getQuestionOptionId() {
        return questionOptionId;
    }

    public void setQuestionOptionId(Integer questionOptionId) {
        this.questionOptionId = questionOptionId;
    }


    public String getQuestionOptionName() {
        return questionOptionName;
    }

    public void setQuestionOptionName(String questionOptionName) {
        this.questionOptionName = questionOptionName;
    }

    public String getQuestionOptionlabel() {
        return questionOptionlabel;
    }

    public void setQuestionOptionlabel(String questionOptionlabel) {
        this.questionOptionlabel = questionOptionlabel;
    }
 
}
