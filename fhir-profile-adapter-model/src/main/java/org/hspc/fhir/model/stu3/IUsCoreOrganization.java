package org.hspc.fhir.model.stu3;

import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Identifier;
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
import java.lang.String;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Address;
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
import org.hl7.fhir.dstu3.model.ContactPoint;
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
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
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
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Resource;
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

public interface IUsCoreOrganization {

	public Organization getAdaptee();

	public void setAdaptee(org.hl7.fhir.dstu3.model.Organization param);

	public boolean hasPartOf();

	public Reference getPartOf();

	public IUsCoreOrganization setPartOf(
			org.hl7.fhir.dstu3.model.Reference param);

	public Organization getPartOfTarget();

	public IUsCoreOrganization setPartOfTarget(
			org.hl7.fhir.dstu3.model.Organization param);

	public boolean hasEndpoint();

	public List<Reference> getEndpoint();

	public List<Identifier> getIdentifier();

	public UsCoreOrganizationAdapter setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param);

	public boolean hasIdentifier();

	public UsCoreOrganizationAdapter addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param);

	public Identifier addIdentifier();

	public List<Organization.OrganizationContactComponent> getContact();

	public UsCoreOrganizationAdapter setContact(
			java.util.List<org.hl7.fhir.dstu3.model.Organization.OrganizationContactComponent> param);

	public boolean hasContact();

	public UsCoreOrganizationAdapter addContact(
			org.hl7.fhir.dstu3.model.Organization.OrganizationContactComponent param);

	public Organization.OrganizationContactComponent addContact();

	public boolean hasAlias();

	public boolean hasAlias(java.lang.String param);

	public IUsCoreOrganization addAlias(java.lang.String param);

	public StringType addAliasElement();

	public List<StringType> getAlias();

	public IUsCoreOrganization setAlias(
			java.util.List<org.hl7.fhir.dstu3.model.StringType> param);

	public List<Address> getAddress();

	public UsCoreOrganizationAdapter setAddress(
			java.util.List<org.hl7.fhir.dstu3.model.Address> param);

	public boolean hasAddress();

	public UsCoreOrganizationAdapter addAddress(
			org.hl7.fhir.dstu3.model.Address param);

	public Address addAddress();

	public boolean hasName();

	public boolean hasNameElement();

	public StringType getNameElement();

	public String getName();

	public IUsCoreOrganization setNameElement(
			org.hl7.fhir.dstu3.model.StringType param);

	public IUsCoreOrganization setName(java.lang.String param);

	public List<ContactPoint> getTelecom();

	public IUsCoreOrganization setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param);

	public boolean hasTelecom();

	public IUsCoreOrganization addTelecom(
			org.hl7.fhir.dstu3.model.ContactPoint param);

	public ContactPoint addTelecom();

	public List<CodeableConcept> getType();

	public IUsCoreOrganization setType(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param);

	public boolean hasType();

	public IUsCoreOrganization addType(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public CodeableConcept addType();

	public boolean hasImplicitRules();

	public boolean hasImplicitRulesElement();

	public UriType getImplicitRulesElement();

	public String getImplicitRules();

	public IUsCoreOrganization setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param);

	public IUsCoreOrganization setImplicitRules(java.lang.String param);

	public boolean hasLanguage();

	public boolean hasLanguageElement();

	public CodeType getLanguageElement();

	public String getLanguage();

	public IUsCoreOrganization setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param);

	public IUsCoreOrganization setLanguage(java.lang.String param);

	public boolean hasId();

	public boolean hasIdElement();

	public IdType getIdElement();

	public String getId();

	public IUsCoreOrganization setIdElement(
			org.hl7.fhir.dstu3.model.IdType param);

	public IUsCoreOrganization setId(java.lang.String param);

	public List<Resource> getContained();

	public IUsCoreOrganization setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param);

	public boolean hasContained();

	public IUsCoreOrganization addContained(
			org.hl7.fhir.dstu3.model.Resource param);

	public boolean hasActive();

	public boolean hasActiveElement();

	public BooleanType getActiveElement();

	public Boolean getActive();

	public IUsCoreOrganization setActiveElement(
			org.hl7.fhir.dstu3.model.BooleanType param);

	public IUsCoreOrganization setActive(java.lang.Boolean param);
}