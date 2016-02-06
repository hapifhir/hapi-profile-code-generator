package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.Condition;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.valueset.ConditionCategoryCodesEnum;
import ca.uhn.fhir.model.dstu2.composite.BoundCodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.RangeDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.valueset.ConditionClinicalStatusCodesEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.dstu2.valueset.ConditionVerificationStatusEnum;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;

public interface IQICoreCondition
{

   public Condition getAdaptee();

   public void setAdaptee(Condition param);

   public List<Condition.Evidence> getEvidence();

   public IQICoreCondition setEvidence(List<Condition.Evidence> param);

   public IQICoreCondition addEvidence(Condition.Evidence param);

   public Condition.Evidence addEvidence();

   public BoundCodeableConceptDt<ConditionCategoryCodesEnum> getCategory();

   public IQICoreCondition setCategory(
         BoundCodeableConceptDt<ConditionCategoryCodesEnum> param);

   public QICoreEncounterAdapter getEncounterResource();

   public IQICoreCondition setEncounterResource(QICoreEncounterAdapter param);

   public ContainedDt getContained();

   public IQICoreCondition setContained(ContainedDt param);

   public DateTimeDt getAbatementDateTimeElement();

   public Date getAbatementDateTime();

   public IQICoreCondition setAbatementDateTime(DateTimeDt param);

   public IQICoreCondition setAbatementDateTime(Date param);

   public QuantityDt getAbatementQuantity();

   public IQICoreCondition setAbatementQuantity(QuantityDt param);

   public BooleanDt getAbatementBooleanElement();

   public Boolean getAbatementBoolean();

   public IQICoreCondition setAbatementBoolean(BooleanDt param);

   public IQICoreCondition setAbatementBoolean(Boolean param);

   public PeriodDt getAbatementPeriod();

   public IQICoreCondition setAbatementPeriod(PeriodDt param);

   public RangeDt getAbatementRange();

   public IQICoreCondition setAbatementRange(RangeDt param);

   public StringDt getAbatementStringElement();

   public String getAbatementString();

   public IQICoreCondition setAbatementString(StringDt param);

   public IQICoreCondition setAbatementString(String param);

   public Condition.Stage getStage();

   public IQICoreCondition setStage(Condition.Stage param);

   public CodeDt getLanguage();

   public IQICoreCondition setLanguage(CodeDt param);

   public StringDt getNotesElement();

   public String getNotes();

   public IQICoreCondition setNotes(String param);

   public IQICoreCondition setNotes(StringDt param);

   public CodeableConceptDt getCriticality();

   public IQICoreCondition setCriticality(CodeableConceptDt param);

   public QICorePatientAdapter getPatientResource();

   public IQICoreCondition setPatientResource(QICorePatientAdapter param);

   public List<IdentifierDt> getIdentifier();

   public IQICoreCondition setIdentifier(List<IdentifierDt> param);

   public IQICoreCondition addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public String getClinicalStatus();

   public IQICoreCondition setClinicalStatus(String param);

   public BoundCodeDt<ConditionClinicalStatusCodesEnum> getClinicalStatusElement();

   public IQICoreCondition setClinicalStatus(
         BoundCodeDt<ConditionClinicalStatusCodesEnum> param);

   public CodeableConceptDt getCode();

   public IQICoreCondition setCode(CodeableConceptDt param);

   public IdDt getId();

   public IQICoreCondition setId(IdDt param);

   public CodeableConceptDt getSeverity();

   public IQICoreCondition setSeverity(CodeableConceptDt param);

   public DateDt getDateRecordedElement();

   public Date getDateRecorded();

   public IQICoreCondition setDateRecorded(Date param);

   public IQICoreCondition setDateRecorded(DateDt param);

   public String getVerificationStatus();

   public IQICoreCondition setVerificationStatus(String param);

   public BoundCodeDt<ConditionVerificationStatusEnum> getVerificationStatusElement();

   public IQICoreCondition setVerificationStatus(
         BoundCodeDt<ConditionVerificationStatusEnum> param);

   public DateTimeDt getOnsetDateTimeElement();

   public Date getOnsetDateTime();

   public IQICoreCondition setOnsetDateTime(DateTimeDt param);

   public IQICoreCondition setOnsetDateTime(Date param);

   public QuantityDt getOnsetQuantity();

   public IQICoreCondition setOnsetQuantity(QuantityDt param);

   public PeriodDt getOnsetPeriod();

   public IQICoreCondition setOnsetPeriod(PeriodDt param);

   public RangeDt getOnsetRange();

   public IQICoreCondition setOnsetRange(RangeDt param);

   public StringDt getOnsetStringElement();

   public String getOnsetString();

   public IQICoreCondition setOnsetString(StringDt param);

   public IQICoreCondition setOnsetString(String param);

   public NarrativeDt getText();

   public IQICoreCondition setText(NarrativeDt param);
}