package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.Specimen;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import java.util.List;
import ca.uhn.fhir.model.primitive.CodeDt;
import org.socraticgrid.fhir.generated.QICoreSpecimenContainer;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.dstu2.valueset.SpecimenStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;

public interface IQICoreSpecimen
{

   public Specimen getAdaptee();

   public void setAdaptee(Specimen param);

   public NarrativeDt getText();

   public IQICoreSpecimen setText(NarrativeDt param);

   public List<Specimen.Treatment> getTreatment();

   public IQICoreSpecimen setTreatment(List<Specimen.Treatment> param);

   public IQICoreSpecimen addTreatment(Specimen.Treatment param);

   public Specimen.Treatment addTreatment();

   public Specimen.Treatment getTreatmentFirstRep();

   public CodeDt getLanguage();

   public IQICoreSpecimen setLanguage(CodeDt param);

   public Specimen.Collection getCollection();

   public IQICoreSpecimen setCollection(Specimen.Collection param);

   public List<QICoreSpecimenContainer> getContainer();

   public IQICoreSpecimen setContainer(List<QICoreSpecimenContainer> param);

   public IQICoreSpecimen addContainer(QICoreSpecimenContainer param);

   public QICoreSpecimenContainer addContainer();

   public IdentifierDt getAccessionIdentifier();

   public IQICoreSpecimen setAccessionIdentifier(IdentifierDt param);

   public ContainedDt getContained();

   public IQICoreSpecimen setContained(ContainedDt param);

   public DateTimeDt getReceivedTimeElement();

   public Date getReceivedTime();

   public IQICoreSpecimen setReceivedTime(Date param);

   public IQICoreSpecimen setReceivedTime(DateTimeDt param);

   public CodeableConceptDt getType();

   public IQICoreSpecimen setType(CodeableConceptDt param);

   public IdDt getId();

   public IQICoreSpecimen setId(IdDt param);

   public String getStatus();

   public IQICoreSpecimen setStatus(String param);

   public BoundCodeDt<SpecimenStatusEnum> getStatusElement();

   public IQICoreSpecimen setStatus(BoundCodeDt<SpecimenStatusEnum> param);

   public List<IdentifierDt> getIdentifier();

   public IQICoreSpecimen setIdentifier(List<IdentifierDt> param);

   public IQICoreSpecimen addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public IdentifierDt getIdentifierFirstRep();
}