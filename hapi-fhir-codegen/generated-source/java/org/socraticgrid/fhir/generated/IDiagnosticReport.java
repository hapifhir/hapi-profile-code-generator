package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.DiagnosticReport;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.InstantDt;
import java.util.Date;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.dstu2.resource.Encounter;
import ca.uhn.fhir.model.dstu2.composite.AttachmentDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.valueset.DiagnosticReportStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;

public interface IDiagnosticReport
{

   public DiagnosticReport getAdaptee();

   public void setAdaptee(DiagnosticReport param);

   public List<IdentifierDt> getIdentifier();

   public IDiagnosticReport setIdentifier(List<IdentifierDt> param);

   public IDiagnosticReport addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public InstantDt getIssuedElement();

   public Date getIssued();

   public IDiagnosticReport setIssued(Date param);

   public IDiagnosticReport setIssued(InstantDt param);

   public IdDt getId();

   public IDiagnosticReport setId(IdDt param);

   public List<DiagnosticReport.Image> getImage();

   public IDiagnosticReport setImage(List<DiagnosticReport.Image> param);

   public IDiagnosticReport addImage(DiagnosticReport.Image param);

   public DiagnosticReport.Image addImage();

   public Encounter getEncounterResource();

   public IDiagnosticReport setEncounterResource(Encounter param);

   public List<AttachmentDt> getPresentedForm();

   public IDiagnosticReport setPresentedForm(List<AttachmentDt> param);

   public IDiagnosticReport addPresentedForm(AttachmentDt param);

   public AttachmentDt addPresentedForm();

   public CodeableConceptDt getCode();

   public IDiagnosticReport setCode(CodeableConceptDt param);

   public CodeableConceptDt getCategory();

   public IDiagnosticReport setCategory(CodeableConceptDt param);

   public String getStatus();

   public IDiagnosticReport setStatus(String param);

   public BoundCodeDt<DiagnosticReportStatusEnum> getStatusElement();

   public IDiagnosticReport setStatus(
         BoundCodeDt<DiagnosticReportStatusEnum> param);

   public ContainedDt getContained();

   public IDiagnosticReport setContained(ContainedDt param);

   public NarrativeDt getText();

   public IDiagnosticReport setText(NarrativeDt param);

   public StringDt getConclusionElement();

   public String getConclusion();

   public IDiagnosticReport setConclusion(String param);

   public IDiagnosticReport setConclusion(StringDt param);

   public CodeDt getLanguage();

   public IDiagnosticReport setLanguage(CodeDt param);

   public DateTimeDt getEffectiveDateTimeElement();

   public Date getEffectiveDateTime();

   public IDiagnosticReport setEffectiveDateTime(DateTimeDt param);

   public IDiagnosticReport setEffectiveDateTime(Date param);

   public PeriodDt getEffectivePeriod();

   public IDiagnosticReport setEffectivePeriod(PeriodDt param);
}