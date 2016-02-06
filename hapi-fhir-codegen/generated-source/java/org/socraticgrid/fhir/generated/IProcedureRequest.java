package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.ProcedureRequest;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.AnnotationDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.valueset.ProcedureRequestPriorityEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.dstu2.valueset.ProcedureRequestStatusEnum;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.TimingDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.resource.Encounter;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.BooleanDt;

public interface IProcedureRequest
{

   public ProcedureRequest getAdaptee();

   public void setAdaptee(ProcedureRequest param);

   public List<AnnotationDt> getNotes();

   public IProcedureRequest setNotes(List<AnnotationDt> param);

   public IProcedureRequest addNotes(AnnotationDt param);

   public AnnotationDt addNotes();

   public IdDt getId();

   public IProcedureRequest setId(IdDt param);

   public CodeDt getLanguage();

   public IProcedureRequest setLanguage(CodeDt param);

   public String getPriority();

   public IProcedureRequest setPriority(String param);

   public BoundCodeDt<ProcedureRequestPriorityEnum> getPriorityElement();

   public IProcedureRequest setPriority(
         BoundCodeDt<ProcedureRequestPriorityEnum> param);

   public CodeableConceptDt getReasonCodeableConcept();

   public IProcedureRequest setReasonCodeableConcept(CodeableConceptDt param);

   public ResourceReferenceDt getReasonReference();

   public IProcedureRequest setReasonReference(ResourceReferenceDt param);

   public NarrativeDt getText();

   public IProcedureRequest setText(NarrativeDt param);

   public String getStatus();

   public IProcedureRequest setStatus(String param);

   public BoundCodeDt<ProcedureRequestStatusEnum> getStatusElement();

   public IProcedureRequest setStatus(
         BoundCodeDt<ProcedureRequestStatusEnum> param);

   public DateTimeDt getScheduledDateTimeElement();

   public Date getScheduledDateTime();

   public IProcedureRequest setScheduledDateTime(DateTimeDt param);

   public IProcedureRequest setScheduledDateTime(Date param);

   public PeriodDt getScheduledPeriod();

   public IProcedureRequest setScheduledPeriod(PeriodDt param);

   public TimingDt getScheduledTiming();

   public IProcedureRequest setScheduledTiming(TimingDt param);

   public ContainedDt getContained();

   public IProcedureRequest setContained(ContainedDt param);

   public Encounter getEncounterResource();

   public IProcedureRequest setEncounterResource(Encounter param);

   public List<IdentifierDt> getIdentifier();

   public IProcedureRequest setIdentifier(List<IdentifierDt> param);

   public IProcedureRequest addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public BooleanDt getAsNeededBooleanElement();

   public Boolean getAsNeededBoolean();

   public IProcedureRequest setAsNeededBoolean(BooleanDt param);

   public IProcedureRequest setAsNeededBoolean(Boolean param);

   public CodeableConceptDt getAsNeededCodeableConcept();

   public IProcedureRequest setAsNeededCodeableConcept(CodeableConceptDt param);

   public DateTimeDt getOrderedOnElement();

   public Date getOrderedOn();

   public IProcedureRequest setOrderedOn(Date param);

   public IProcedureRequest setOrderedOn(DateTimeDt param);

   public CodeableConceptDt getCode();

   public IProcedureRequest setCode(CodeableConceptDt param);
}