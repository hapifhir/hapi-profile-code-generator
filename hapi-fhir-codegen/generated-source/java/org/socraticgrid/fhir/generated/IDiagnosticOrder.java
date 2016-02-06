package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.DiagnosticOrder;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.resource.Practitioner;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.dstu2.resource.Encounter;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticOrderPriorityEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticOrderStatusEnum;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.AnnotationDt;
import ca.uhn.fhir.model.primitive.IdDt;

public interface IDiagnosticOrder
{

   public DiagnosticOrder getAdaptee();

   public void setAdaptee(DiagnosticOrder param);

   public Practitioner getOrdererResource();

   public IDiagnosticOrder setOrdererResource(Practitioner param);

   public List<DiagnosticOrder.Event> getEvent();

   public IDiagnosticOrder setEvent(List<DiagnosticOrder.Event> param);

   public IDiagnosticOrder addEvent(DiagnosticOrder.Event param);

   public DiagnosticOrder.Event addEvent();

   public NarrativeDt getText();

   public IDiagnosticOrder setText(NarrativeDt param);

   public Encounter getEncounterResource();

   public IDiagnosticOrder setEncounterResource(Encounter param);

   public ContainedDt getContained();

   public IDiagnosticOrder setContained(ContainedDt param);

   public String getPriority();

   public IDiagnosticOrder setPriority(String param);

   public BoundCodeDt<DiagnosticOrderPriorityEnum> getPriorityElement();

   public IDiagnosticOrder setPriority(
         BoundCodeDt<DiagnosticOrderPriorityEnum> param);

   public String getStatus();

   public IDiagnosticOrder setStatus(String param);

   public BoundCodeDt<DiagnosticOrderStatusEnum> getStatusElement();

   public IDiagnosticOrder setStatus(
         BoundCodeDt<DiagnosticOrderStatusEnum> param);

   public CodeDt getLanguage();

   public IDiagnosticOrder setLanguage(CodeDt param);

   public List<DiagnosticOrder.Item> getItem();

   public IDiagnosticOrder setItem(List<DiagnosticOrder.Item> param);

   public IDiagnosticOrder addItem(DiagnosticOrder.Item param);

   public DiagnosticOrder.Item addItem();

   public List<IdentifierDt> getIdentifier();

   public IDiagnosticOrder setIdentifier(List<IdentifierDt> param);

   public IDiagnosticOrder addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public List<AnnotationDt> getNote();

   public IDiagnosticOrder setNote(List<AnnotationDt> param);

   public IDiagnosticOrder addNote(AnnotationDt param);

   public AnnotationDt addNote();

   public IdDt getId();

   public IDiagnosticOrder setId(IdDt param);
}