package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreLocation;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.ContactPoint;
import java.util.List;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import java.lang.String;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreLocationAdapter implements IUsCoreLocation {

	private Location adaptedClass;

	public UsCoreLocationAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Location();
	}

	public UsCoreLocationAdapter(org.hl7.fhir.dstu3.model.Location adaptee) {
		this.adaptedClass = adaptee;
	}

	public Location getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Location param) {
		this.adaptedClass = param;
	}

	public Location.LocationPositionComponent getPosition() {
		try {
			return adaptedClass.getPosition();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Position", e);
		}
	}

	public UsCoreLocationAdapter setPosition(
			org.hl7.fhir.dstu3.model.Location.LocationPositionComponent param) {
		adaptedClass.setPosition(param);
		return this;
	}

	public boolean hasPosition() {
		return adaptedClass.hasPosition();
	}

	public Address getAddress() {
		try {
			return adaptedClass.getAddress();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Address", e);
		}
	}

	public UsCoreLocationAdapter setAddress(
			org.hl7.fhir.dstu3.model.Address param) {
		adaptedClass.setAddress(param);
		return this;
	}

	public boolean hasAddress() {
		return adaptedClass.hasAddress();
	}

	public List<ContactPoint> getTelecom() {
		try {
			return adaptedClass.getTelecom();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Telecom", e);
		}
	}

	public IUsCoreLocation setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param) {
		adaptedClass.setTelecom(param);
		return this;
	}

	public boolean hasTelecom() {
		return adaptedClass.hasTelecom();
	}

	public IUsCoreLocation addTelecom(
			org.hl7.fhir.dstu3.model.ContactPoint param) {
		adaptedClass.addTelecom(param);
		return this;
	}

	public ContactPoint addTelecom() {
		return adaptedClass.addTelecom();
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

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreLocation setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreLocation addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasAlias() {
		return adaptedClass.hasAlias();
	}

	public boolean hasAlias(java.lang.String param) {
		return adaptedClass.hasAlias(param);
	}

	public IUsCoreLocation addAlias(java.lang.String param) {
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

	public IUsCoreLocation setAlias(
			java.util.List<org.hl7.fhir.dstu3.model.StringType> param) {
		adaptedClass.setAlias(param);
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

	public IUsCoreLocation setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreLocation setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public CodeableConcept getPhysicalType() {
		try {
			return adaptedClass.getPhysicalType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PhysicalType", e);
		}
	}

	public IUsCoreLocation setPhysicalType(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setPhysicalType(param);
		return this;
	}

	public boolean hasPhysicalType() {
		return adaptedClass.hasPhysicalType();
	}

	public boolean hasDescription() {
		return adaptedClass.hasDescription();
	}

	public boolean hasDescriptionElement() {
		return adaptedClass.hasDescriptionElement();
	}

	public StringType getDescriptionElement() {
		try {
			return adaptedClass.getDescriptionElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DescriptionElement", e);
		}
	}

	public String getDescription() {
		try {
			return adaptedClass.getDescription();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Description", e);
		}
	}

	public IUsCoreLocation setDescriptionElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setDescriptionElement(param);
		return this;
	}

	public IUsCoreLocation setDescription(java.lang.String param) {
		adaptedClass.setDescription(param);
		return this;
	}

	public Coding getOperationalStatus() {
		try {
			return adaptedClass.getOperationalStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OperationalStatus", e);
		}
	}

	public IUsCoreLocation setOperationalStatus(
			org.hl7.fhir.dstu3.model.Coding param) {
		adaptedClass.setOperationalStatus(param);
		return this;
	}

	public boolean hasOperationalStatus() {
		return adaptedClass.hasOperationalStatus();
	}

	public boolean hasMode() {
		return adaptedClass.hasMode();
	}

	public boolean hasModeElement() {
		return adaptedClass.hasModeElement();
	}

	public Location.LocationMode getMode() {
		try {
			return adaptedClass.getMode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Mode", e);
		}
	}

	public Enumeration<Location.LocationMode> getModeElement() {
		try {
			return adaptedClass.getModeElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ModeElement", e);
		}
	}

	public IUsCoreLocation setMode(
			org.hl7.fhir.dstu3.model.Location.LocationMode param) {
		adaptedClass.setMode(param);
		return this;
	}

	public IUsCoreLocation setModeElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Location.LocationMode> param) {
		adaptedClass.setModeElement(param);
		return this;
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

	public IUsCoreLocation setPartOf(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPartOf(param);
		return this;
	}

	public Location getPartOfTarget() {
		return (org.hl7.fhir.dstu3.model.Location) adaptedClass
				.getPartOfTarget();
	}

	public IUsCoreLocation setPartOfTarget(
			org.hl7.fhir.dstu3.model.Location param) {
		adaptedClass.setPartOfTarget(param);
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

	public IUsCoreLocation setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreLocation setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
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

	public IUsCoreLocation setNameElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setNameElement(param);
		return this;
	}

	public IUsCoreLocation setName(java.lang.String param) {
		adaptedClass.setName(param);
		return this;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreLocation setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreLocation addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasManagingOrganization() {
		return adaptedClass.hasManagingOrganization();
	}

	public Reference getManagingOrganization() {
		try {
			return adaptedClass.getManagingOrganization();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManagingOrganization", e);
		}
	}

	public IUsCoreLocation setManagingOrganization(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setManagingOrganization(param);
		return this;
	}

	public Organization getManagingOrganizationTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getManagingOrganizationTarget();
	}

	public IUsCoreLocation setManagingOrganizationTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setManagingOrganizationTarget(param);
		return this;
	}

	public UsCoreOrganizationAdapter getManagingOrganizationAdapterTarget() {
		if (adaptedClass.getManagingOrganization().getResource() instanceof org.hl7.fhir.dstu3.model.Organization) {
			org.hspc.fhir.model.stu3.UsCoreOrganizationAdapter profiledType = new org.hspc.fhir.model.stu3.UsCoreOrganizationAdapter();
			profiledType
					.setAdaptee((org.hl7.fhir.dstu3.model.Organization) adaptedClass
							.getManagingOrganization().getResource());
			return profiledType;
		} else {
			return null;
		}
	}

	public IUsCoreLocation setManagingOrganizationAdapterTarget(
			org.hspc.fhir.model.stu3.UsCoreOrganizationAdapter param) {
		adaptedClass.setManagingOrganizationTarget(param.getAdaptee());
		return this;
	}

	public CodeableConcept getType() {
		try {
			return adaptedClass.getType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Type", e);
		}
	}

	public IUsCoreLocation setType(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setType(param);
		return this;
	}

	public boolean hasType() {
		return adaptedClass.hasType();
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

	public IUsCoreLocation setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreLocation setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Location.LocationStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Location.LocationStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreLocation setStatus(
			org.hl7.fhir.dstu3.model.Location.LocationStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreLocation setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Location.LocationStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}
}