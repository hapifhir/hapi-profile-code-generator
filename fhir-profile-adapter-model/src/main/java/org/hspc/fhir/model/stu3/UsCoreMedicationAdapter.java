package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreMedication;
import org.hl7.fhir.dstu3.model.Medication;
import java.util.List;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreMedicationAdapter implements IUsCoreMedication {

	private Medication adaptedClass;

	public UsCoreMedicationAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Medication();
	}

	public UsCoreMedicationAdapter(org.hl7.fhir.dstu3.model.Medication adaptee) {
		this.adaptedClass = adaptee;
	}

	public Medication getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Medication param) {
		this.adaptedClass = param;
	}

	public List<Medication.MedicationIngredientComponent> getIngredient() {
		try {
			return adaptedClass.getIngredient();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Ingredient", e);
		}
	}

	public UsCoreMedicationAdapter setIngredient(
			java.util.List<org.hl7.fhir.dstu3.model.Medication.MedicationIngredientComponent> param) {
		adaptedClass.setIngredient(param);
		return this;
	}

	public boolean hasIngredient() {
		return adaptedClass.hasIngredient();
	}

	public UsCoreMedicationAdapter addIngredient(
			org.hl7.fhir.dstu3.model.Medication.MedicationIngredientComponent param) {
		adaptedClass.addIngredient(param);
		return this;
	}

	public Medication.MedicationIngredientComponent addIngredient() {
		return adaptedClass.addIngredient();
	}

	public Medication.MedicationPackageComponent getPackage() {
		try {
			return adaptedClass.getPackage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Package", e);
		}
	}

	public UsCoreMedicationAdapter setPackage(
			org.hl7.fhir.dstu3.model.Medication.MedicationPackageComponent param) {
		adaptedClass.setPackage(param);
		return this;
	}

	public boolean hasPackage() {
		return adaptedClass.hasPackage();
	}

	public boolean hasIsBrand() {
		return adaptedClass.hasIsBrand();
	}

	public boolean hasIsBrandElement() {
		return adaptedClass.hasIsBrandElement();
	}

	public BooleanType getIsBrandElement() {
		try {
			return adaptedClass.getIsBrandElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IsBrandElement", e);
		}
	}

	public Boolean getIsBrand() {
		try {
			return adaptedClass.getIsBrand();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IsBrand", e);
		}
	}

	public IUsCoreMedication setIsBrandElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setIsBrandElement(param);
		return this;
	}

	public IUsCoreMedication setIsBrand(java.lang.Boolean param) {
		adaptedClass.setIsBrand(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreMedication setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreMedication addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasManufacturer() {
		return adaptedClass.hasManufacturer();
	}

	public Reference getManufacturer() {
		try {
			return adaptedClass.getManufacturer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Manufacturer", e);
		}
	}

	public IUsCoreMedication setManufacturer(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setManufacturer(param);
		return this;
	}

	public Organization getManufacturerTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getManufacturerTarget();
	}

	public IUsCoreMedication setManufacturerTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setManufacturerTarget(param);
		return this;
	}

	public boolean hasId() {
		return adaptedClass.hasId();
	}

	public boolean hasIdElement() {
		return adaptedClass.hasIdElement();
	}

	public IdType getIdElement() {
		try {
			return adaptedClass.getIdElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IdElement", e);
		}
	}

	public String getId() {
		try {
			return adaptedClass.getId();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Id", e);
		}
	}

	public IUsCoreMedication setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreMedication setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public boolean hasLanguage() {
		return adaptedClass.hasLanguage();
	}

	public boolean hasLanguageElement() {
		return adaptedClass.hasLanguageElement();
	}

	public CodeType getLanguageElement() {
		try {
			return adaptedClass.getLanguageElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LanguageElement", e);
		}
	}

	public String getLanguage() {
		try {
			return adaptedClass.getLanguage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Language", e);
		}
	}

	public IUsCoreMedication setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreMedication setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public boolean hasIsOverTheCounter() {
		return adaptedClass.hasIsOverTheCounter();
	}

	public boolean hasIsOverTheCounterElement() {
		return adaptedClass.hasIsOverTheCounterElement();
	}

	public BooleanType getIsOverTheCounterElement() {
		try {
			return adaptedClass.getIsOverTheCounterElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IsOverTheCounterElement",
					e);
		}
	}

	public Boolean getIsOverTheCounter() {
		try {
			return adaptedClass.getIsOverTheCounter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IsOverTheCounter", e);
		}
	}

	public IUsCoreMedication setIsOverTheCounterElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setIsOverTheCounterElement(param);
		return this;
	}

	public IUsCoreMedication setIsOverTheCounter(java.lang.Boolean param) {
		adaptedClass.setIsOverTheCounter(param);
		return this;
	}

	public boolean hasImplicitRules() {
		return adaptedClass.hasImplicitRules();
	}

	public boolean hasImplicitRulesElement() {
		return adaptedClass.hasImplicitRulesElement();
	}

	public UriType getImplicitRulesElement() {
		try {
			return adaptedClass.getImplicitRulesElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ImplicitRulesElement", e);
		}
	}

	public String getImplicitRules() {
		try {
			return adaptedClass.getImplicitRules();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ImplicitRules", e);
		}
	}

	public IUsCoreMedication setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreMedication setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public List<Attachment> getImage() {
		try {
			return adaptedClass.getImage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Image", e);
		}
	}

	public IUsCoreMedication setImage(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param) {
		adaptedClass.setImage(param);
		return this;
	}

	public boolean hasImage() {
		return adaptedClass.hasImage();
	}

	public IUsCoreMedication addImage(org.hl7.fhir.dstu3.model.Attachment param) {
		adaptedClass.addImage(param);
		return this;
	}

	public Attachment addImage() {
		return adaptedClass.addImage();
	}

	public CodeableConcept getForm() {
		try {
			return adaptedClass.getForm();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Form", e);
		}
	}

	public IUsCoreMedication setForm(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setForm(param);
		return this;
	}

	public boolean hasForm() {
		return adaptedClass.hasForm();
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Medication.MedicationStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Medication.MedicationStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreMedication setStatus(
			org.hl7.fhir.dstu3.model.Medication.MedicationStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreMedication setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Medication.MedicationStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreMedication setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}
}