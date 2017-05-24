package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreOrganization;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Reference;
import java.util.List;
import org.hl7.fhir.dstu3.model.Identifier;
import java.lang.String;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreOrganizationAdapter implements IUsCoreOrganization {

	private Organization adaptedClass;

	public UsCoreOrganizationAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Organization();
	}

	public UsCoreOrganizationAdapter(
			org.hl7.fhir.dstu3.model.Organization adaptee) {
		this.adaptedClass = adaptee;
	}

	public Organization getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Organization param) {
		this.adaptedClass = param;
	}

	public boolean hasPartOf() {
		return adaptedClass.hasPartOf();
	}

	public Reference getPartOf() {
		try {
			return adaptedClass.getPartOf();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PartOf", e);
		}
	}

	public IUsCoreOrganization setPartOf(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPartOf(param);
		return this;
	}

	public Organization getPartOfTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getPartOfTarget();
	}

	public IUsCoreOrganization setPartOfTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setPartOfTarget(param);
		return this;
	}

	public boolean hasEndpoint() {
		return adaptedClass.hasEndpoint();
	}

	public List<Reference> getEndpoint() {
		try {
			return adaptedClass.getEndpoint();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Endpoint", e);
		}
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public UsCoreOrganizationAdapter setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public UsCoreOrganizationAdapter addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public List<Organization.OrganizationContactComponent> getContact() {
		try {
			return adaptedClass.getContact();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contact", e);
		}
	}

	public UsCoreOrganizationAdapter setContact(
			java.util.List<org.hl7.fhir.dstu3.model.Organization.OrganizationContactComponent> param) {
		adaptedClass.setContact(param);
		return this;
	}

	public boolean hasContact() {
		return adaptedClass.hasContact();
	}

	public UsCoreOrganizationAdapter addContact(
			org.hl7.fhir.dstu3.model.Organization.OrganizationContactComponent param) {
		adaptedClass.addContact(param);
		return this;
	}

	public Organization.OrganizationContactComponent addContact() {
		return adaptedClass.addContact();
	}

	public boolean hasAlias() {
		return adaptedClass.hasAlias();
	}

	public boolean hasAlias(java.lang.String param) {
		return adaptedClass.hasAlias(param);
	}

	public IUsCoreOrganization addAlias(java.lang.String param) {
		adaptedClass.addAlias(param);
		return this;
	}

	public StringType addAliasElement() {
		return adaptedClass.addAliasElement();
	}

	public List<StringType> getAlias() {
		try {
			return adaptedClass.getAlias();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Alias", e);
		}
	}

	public IUsCoreOrganization setAlias(
			java.util.List<org.hl7.fhir.dstu3.model.StringType> param) {
		adaptedClass.setAlias(param);
		return this;
	}

	public List<Address> getAddress() {
		try {
			return adaptedClass.getAddress();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Address", e);
		}
	}

	public UsCoreOrganizationAdapter setAddress(
			java.util.List<org.hl7.fhir.dstu3.model.Address> param) {
		adaptedClass.setAddress(param);
		return this;
	}

	public boolean hasAddress() {
		return adaptedClass.hasAddress();
	}

	public UsCoreOrganizationAdapter addAddress(
			org.hl7.fhir.dstu3.model.Address param) {
		adaptedClass.addAddress(param);
		return this;
	}

	public Address addAddress() {
		return adaptedClass.addAddress();
	}

	public boolean hasName() {
		return adaptedClass.hasName();
	}

	public boolean hasNameElement() {
		return adaptedClass.hasNameElement();
	}

	public StringType getNameElement() {
		try {
			return adaptedClass.getNameElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NameElement", e);
		}
	}

	public String getName() {
		try {
			return adaptedClass.getName();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Name", e);
		}
	}

	public IUsCoreOrganization setNameElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setNameElement(param);
		return this;
	}

	public IUsCoreOrganization setName(java.lang.String param) {
		adaptedClass.setName(param);
		return this;
	}

	public List<ContactPoint> getTelecom() {
		try {
			return adaptedClass.getTelecom();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Telecom", e);
		}
	}

	public IUsCoreOrganization setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param) {
		adaptedClass.setTelecom(param);
		return this;
	}

	public boolean hasTelecom() {
		return adaptedClass.hasTelecom();
	}

	public IUsCoreOrganization addTelecom(
			org.hl7.fhir.dstu3.model.ContactPoint param) {
		adaptedClass.addTelecom(param);
		return this;
	}

	public ContactPoint addTelecom() {
		return adaptedClass.addTelecom();
	}

	public List<CodeableConcept> getType() {
		try {
			return adaptedClass.getType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Type", e);
		}
	}

	public IUsCoreOrganization setType(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setType(param);
		return this;
	}

	public boolean hasType() {
		return adaptedClass.hasType();
	}

	public IUsCoreOrganization addType(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addType(param);
		return this;
	}

	public CodeableConcept addType() {
		return adaptedClass.addType();
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

	public IUsCoreOrganization setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreOrganization setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
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

	public IUsCoreOrganization setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreOrganization setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
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

	public IUsCoreOrganization setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreOrganization setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreOrganization setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreOrganization addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasActive() {
		return adaptedClass.hasActive();
	}

	public boolean hasActiveElement() {
		return adaptedClass.hasActiveElement();
	}

	public BooleanType getActiveElement() {
		try {
			return adaptedClass.getActiveElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ActiveElement", e);
		}
	}

	public Boolean getActive() {
		try {
			return adaptedClass.getActive();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Active", e);
		}
	}

	public IUsCoreOrganization setActiveElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setActiveElement(param);
		return this;
	}

	public IUsCoreOrganization setActive(java.lang.Boolean param) {
		adaptedClass.setActive(param);
		return this;
	}
}