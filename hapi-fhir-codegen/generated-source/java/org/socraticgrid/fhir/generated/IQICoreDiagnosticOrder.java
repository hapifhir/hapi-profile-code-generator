package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.DiagnosticOrder;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticOrderPriorityEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticOrderStatusEnum;
import org.socraticgrid.fhir.generated.QICoreDiagnosticOrderItem;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.AnnotationDt;
import ca.uhn.fhir.model.primitive.IdDt;

public interface IQICoreDiagnosticOrder
{

   public DiagnosticOrder getAdaptee();

   public void setAdaptee(DiagnosticOrder param);

   public QICorePractitionerAdapter getOrdererResource();

   public IQICoreDiagnosticOrder setOrdererResource(
         QICorePractitionerAdapter param);

   public List<DiagnosticOrder.Event> getEvent();

   public IQICoreDiagnosticOrder setEvent(List<DiagnosticOrder.Event> param);

   public IQICoreDiagnosticOrder addEvent(DiagnosticOrder.Event param);

   public DiagnosticOrder.Event addEvent();

   public NarrativeDt getText();

   public IQICoreDiagnosticOrder setText(NarrativeDt param);

   public QICoreEncounterAdapter getEncounterResource();

   public IQICoreDiagnosticOrder setEncounterResource(
         QICoreEncounterAdapter param);

   public ContainedDt getContained();

   public IQICoreDiagnosticOrder setContained(ContainedDt param);

   public String getPriority();

   public IQICoreDiagnosticOrder setPriority(String param);

   public BoundCodeDt<DiagnosticOrderPriorityEnum> getPriorityElement();

   public IQICoreDiagnosticOrder setPriority(
         BoundCodeDt<DiagnosticOrderPriorityEnum> param);

   public String getStatus();

   public IQICoreDiagnosticOrder setStatus(String param);

   public BoundCodeDt<DiagnosticOrderStatusEnum> getStatusElement();

   public IQICoreDiagnosticOrder setStatus(
         BoundCodeDt<DiagnosticOrderStatusEnum> param);

   public List<QICoreDiagnosticOrderItem> getItem();

   public IQICoreDiagnosticOrder setItem(List<QICoreDiagnosticOrderItem> param);

   public IQICoreDiagnosticOrder addItem(QICoreDiagnosticOrderItem param);

   public QICoreDiagnosticOrderItem addItem();

   public CodeDt getLanguage();

   public IQICoreDiagnosticOrder setLanguage(CodeDt param);

   public List<IdentifierDt> getIdentifier();

   public IQICoreDiagnosticOrder setIdentifier(List<IdentifierDt> param);

   public IQICoreDiagnosticOrder addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public CodeableConceptDt getReasonRejected();

   public IQICoreDiagnosticOrder setReasonRejected(CodeableConceptDt param);

   public List<AnnotationDt> getNote();

   public IQICoreDiagnosticOrder setNote(List<AnnotationDt> param);

   public IQICoreDiagnosticOrder addNote(AnnotationDt param);

   public AnnotationDt addNote();

   public IdDt getId();

   public IQICoreDiagnosticOrder setId(IdDt param);
}