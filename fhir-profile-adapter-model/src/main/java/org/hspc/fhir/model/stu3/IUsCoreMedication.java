package org.hspc.fhir.model.stu3;

import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import java.lang.String;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;

public interface IUsCoreMedication {

	public Medication getAdaptee();

	public void setAdaptee(org.hl7.fhir.dstu3.model.Medication param);

	public List<Medication.MedicationIngredientComponent> getIngredient();

	public UsCoreMedicationAdapter setIngredient(
			java.util.List<org.hl7.fhir.dstu3.model.Medication.MedicationIngredientComponent> param);

	public boolean hasIngredient();

	public UsCoreMedicationAdapter addIngredient(
			org.hl7.fhir.dstu3.model.Medication.MedicationIngredientComponent param);

	public Medication.MedicationIngredientComponent addIngredient();

	public Medication.MedicationPackageComponent getPackage();

	public UsCoreMedicationAdapter setPackage(
			org.hl7.fhir.dstu3.model.Medication.MedicationPackageComponent param);

	public boolean hasPackage();

	public boolean hasIsBrand();

	public boolean hasIsBrandElement();

	public BooleanType getIsBrandElement();

	public Boolean getIsBrand();

	public IUsCoreMedication setIsBrandElement(
			org.hl7.fhir.dstu3.model.BooleanType param);

	public IUsCoreMedication setIsBrand(java.lang.Boolean param);

	public List<Resource> getContained();

	public IUsCoreMedication setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param);

	public boolean hasContained();

	public IUsCoreMedication addContained(
			org.hl7.fhir.dstu3.model.Resource param);

	public boolean hasManufacturer();

	public Reference getManufacturer();

	public IUsCoreMedication setManufacturer(
			org.hl7.fhir.dstu3.model.Reference param);

	public Organization getManufacturerTarget();

	public IUsCoreMedication setManufacturerTarget(
			org.hl7.fhir.dstu3.model.Organization param);

	public boolean hasId();

	public boolean hasIdElement();

	public IdType getIdElement();

	public String getId();

	public IUsCoreMedication setIdElement(org.hl7.fhir.dstu3.model.IdType param);

	public IUsCoreMedication setId(java.lang.String param);

	public boolean hasLanguage();

	public boolean hasLanguageElement();

	public CodeType getLanguageElement();

	public String getLanguage();

	public IUsCoreMedication setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param);

	public IUsCoreMedication setLanguage(java.lang.String param);

	public boolean hasIsOverTheCounter();

	public boolean hasIsOverTheCounterElement();

	public BooleanType getIsOverTheCounterElement();

	public Boolean getIsOverTheCounter();

	public IUsCoreMedication setIsOverTheCounterElement(
			org.hl7.fhir.dstu3.model.BooleanType param);

	public IUsCoreMedication setIsOverTheCounter(java.lang.Boolean param);

	public boolean hasImplicitRules();

	public boolean hasImplicitRulesElement();

	public UriType getImplicitRulesElement();

	public String getImplicitRules();

	public IUsCoreMedication setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param);

	public IUsCoreMedication setImplicitRules(java.lang.String param);

	public List<Attachment> getImage();

	public IUsCoreMedication setImage(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param);

	public boolean hasImage();

	public IUsCoreMedication addImage(org.hl7.fhir.dstu3.model.Attachment param);

	public Attachment addImage();

	public CodeableConcept getForm();

	public IUsCoreMedication setForm(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public boolean hasForm();

	public boolean hasStatus();

	public boolean hasStatusElement();

	public Medication.MedicationStatus getStatus();

	public Enumeration<Medication.MedicationStatus> getStatusElement();

	public IUsCoreMedication setStatus(
			org.hl7.fhir.dstu3.model.Medication.MedicationStatus param);

	public IUsCoreMedication setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Medication.MedicationStatus> param);

	public CodeableConcept getCode();

	public IUsCoreMedication setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public boolean hasCode();
}