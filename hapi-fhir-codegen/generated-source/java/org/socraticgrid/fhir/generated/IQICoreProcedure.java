package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.Procedure;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.AnnotationDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.dstu2.valueset.ProcedureStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.primitive.CodeDt;

public interface IQICoreProcedure
{

   public Procedure getAdaptee();

   public void setAdaptee(Procedure param);

   public QICoreLocationAdapter getLocationResource();

   public IQICoreProcedure setLocationResource(QICoreLocationAdapter param);

   public CodeableConceptDt getOutcome();

   public IQICoreProcedure setOutcome(CodeableConceptDt param);

   public List<Procedure.FocalDevice> getFocalDevice();

   public IQICoreProcedure setFocalDevice(List<Procedure.FocalDevice> param);

   public IQICoreProcedure addFocalDevice(Procedure.FocalDevice param);

   public Procedure.FocalDevice addFocalDevice();

   public List<IdentifierDt> getIdentifier();

   public IQICoreProcedure setIdentifier(List<IdentifierDt> param);

   public IQICoreProcedure addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public CodeableConceptDt getCategory();

   public IQICoreProcedure setCategory(CodeableConceptDt param);

   public DateTimeDt getPerformedDateTimeElement();

   public Date getPerformedDateTime();

   public IQICoreProcedure setPerformedDateTime(DateTimeDt param);

   public IQICoreProcedure setPerformedDateTime(Date param);

   public PeriodDt getPerformedPeriod();

   public IQICoreProcedure setPerformedPeriod(PeriodDt param);

   public List<Procedure.Performer> getPerformer();

   public IQICoreProcedure setPerformer(List<Procedure.Performer> param);

   public IQICoreProcedure addPerformer(Procedure.Performer param);

   public Procedure.Performer addPerformer();

   public List<AnnotationDt> getNotes();

   public IQICoreProcedure setNotes(List<AnnotationDt> param);

   public IQICoreProcedure addNotes(AnnotationDt param);

   public AnnotationDt addNotes();

   public NarrativeDt getText();

   public IQICoreProcedure setText(NarrativeDt param);

   public CodeableConceptDt getReasonCodeableConcept();

   public IQICoreProcedure setReasonCodeableConcept(CodeableConceptDt param);

   public ResourceReferenceDt getReasonReference();

   public IQICoreProcedure setReasonReference(ResourceReferenceDt param);

   public DateTimeDt getIncisionDateTime();

   public IQICoreProcedure setIncisionDateTime(DateTimeDt param);

   public IdDt getId();

   public IQICoreProcedure setId(IdDt param);

   public BooleanDt getNotPerformedElement();

   public Boolean getNotPerformed();

   public IQICoreProcedure setNotPerformed(Boolean param);

   public IQICoreProcedure setNotPerformed(BooleanDt param);

   public String getStatus();

   public IQICoreProcedure setStatus(String param);

   public BoundCodeDt<ProcedureStatusEnum> getStatusElement();

   public IQICoreProcedure setStatus(BoundCodeDt<ProcedureStatusEnum> param);

   public CodeableConceptDt getCode();

   public IQICoreProcedure setCode(CodeableConceptDt param);

   public List<ResourceReferenceDt> getApproachBodySite();

   public IQICoreProcedure setApproachBodySite(List<ResourceReferenceDt> param);

   public QICoreEncounterAdapter getEncounterResource();

   public IQICoreProcedure setEncounterResource(QICoreEncounterAdapter param);

   public ContainedDt getContained();

   public IQICoreProcedure setContained(ContainedDt param);

   public CodeDt getLanguage();

   public IQICoreProcedure setLanguage(CodeDt param);
}