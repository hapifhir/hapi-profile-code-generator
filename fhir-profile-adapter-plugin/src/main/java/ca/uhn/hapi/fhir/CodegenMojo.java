package ca.uhn.hapi.fhir;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import ca.uhn.fhir.utils.codegen.hapi.CodeGeneratorConfigurator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "generate-profile-structures", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodegenMojo
        extends AbstractMojo {
    private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(CodegenMojo.class);
    /**
     * Location of the file.
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    @Parameter(property = "property")
    private File outputDirectory;

    @Parameter(required = false)
    private List<String> profileSourceFilePaths;

    @Parameter(required = false)
    private List<String> profileDirectoryPaths;

    @Parameter(required = false)
    private List<String> extensionRepositories;

    @Parameter(required = false)
    private List<String> profileNames;

    @Parameter(alias = "package", required = false)
    private String packageName;

    @Parameter(alias = "version", required = false, defaultValue = "stu3")
    private String resourceVersion;

    @Parameter(required = true)
    private String buildDatatypes;

    @Parameter(required = true)
    private String sourceGenerationDirectory;

    @Parameter(required = false)
    private String configFilePath;

    @Parameter(required = false, defaultValue = "${project.build.directory}")
    private String buildDir;

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public List<String> getProfileSourceFilePaths() {
        return profileSourceFilePaths;
    }

    public void setProfileSourceFilePaths(List<String> profileSourceFilePaths) {
        this.profileSourceFilePaths = profileSourceFilePaths;
    }

    public List<String> getProfileDirectoryPaths() {
        return profileDirectoryPaths;
    }

    public void setProfileDirectoryPaths(List<String> profileDirectoryPaths) {
        this.profileDirectoryPaths = profileDirectoryPaths;
    }

    public List<String> getExtensionRepositories() {
        return extensionRepositories;
    }

    public void setExtensionRepositories(List<String> extensionRepositories) {
        this.extensionRepositories = extensionRepositories;
    }

    public List<String> getProfileNames() {
        return profileNames;
    }

    public void setProfileNames(List<String> profileNames) {
        this.profileNames = profileNames;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public String getBuildDatatypes() {
        return buildDatatypes;
    }

    public void setBuildDatatypes(String buildDatatypes) {
        this.buildDatatypes = buildDatatypes;
    }


    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException {

        if (StringUtils.isBlank(packageName)) {
            throw new MojoFailureException("Package not specified");
        }
        if (packageName.contains("..") || packageName.endsWith(".")) {
            throw new MojoFailureException("Invalid package specified");
        }

        String baseDir = this.buildDir + File.separatorChar + ".." + File.separatorChar;

        String targetCodeGenerationDirectory = baseDir + sourceGenerationDirectory;

        File f = new File(new File(targetCodeGenerationDirectory), packageName.replace('.', File.separatorChar));

        if (f.exists()) {
//            deleteDirectory(f);
        }

        if (!f.exists()) {
            f.mkdirs();
        }


        CodeGeneratorConfigurator configurator = new CodeGeneratorConfigurator(this.configFilePath);
        configurator.setProjectBaseDirectory(baseDir);
        configurator.setProjectBuildDirectory(buildDir + File.separatorChar + "classes");
        configurator.setGeneratedCodePackage(this.packageName);
        configurator.setProfileDirectoryPaths(this.profileDirectoryPaths);
        configurator.setProfileFilePaths(this.profileSourceFilePaths);
        configurator.setExtensionRepositories(this.extensionRepositories);
        configurator.setProfileNameList(this.profileNames);
        configurator.setTargetCodeGenerationDirectory(targetCodeGenerationDirectory);
        if (this.configFilePath != null)
            configurator.initialize();
        ca.uhn.fhir.utils.codegen.hapi.Main.generateDstu3Code(configurator, false);
    }

    public String getSourceGenerationDirectory() {
        return sourceGenerationDirectory;
    }

    public void setSourceGenerationDirectory(String sourceGenerationDirectory) {
        this.sourceGenerationDirectory = sourceGenerationDirectory;
    }

    private boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public static void main(String[] args) throws Exception {
        CodegenMojo codegenMojo = new CodegenMojo();
        codegenMojo.setProfileSourceFilePaths(Arrays.asList(new String[]{"D:/tutorial/LogicalViewGenerator/hapi-fhir-codegen/src/test/resources/us-core3-profile/profile-definitions/profiles-resources.xml"}));
        codegenMojo.setProfileDirectoryPaths(Arrays.asList(new String[]{"D:/tutorial/LogicalViewGenerator/hapi-fhir-codegen/src/test/resources/us-core3-profile/"}));
        codegenMojo.setSourceGenerationDirectory("D:/tutorial/LogicalViewGenerator/fhir-profile-codegen-plugin/src/main/generated-java");
        codegenMojo.setPackageName("org.hspc.fhir.model.stu");
        codegenMojo.setExtensionRepositories(Arrays.asList(new String[]{"C:/Users/lpnrahma/repository/fhir_dstu3/fhir/trunk/build/publish/",
                "D:/tutorial/LogicalViewGenerator/hapi-fhir-codegen/src/test/resources/us-core3-profile/"}));
        codegenMojo.setProfileNames(Arrays.asList(new String[]{"us-core-allergyintolerance",
                "us-core-careteam",
                "us-core-condition",
                "us-core-device",
                "us-core-diagnosticreport",
                "us-core-goal",
                "us-core-immunization",
                "us-core-location",
                "us-core-medication",
                "us-core-medicationrequest",
                "us-core-medicationstatement",
                "us-core-practitioner",
                "us-core-procedure",
                "us-core-observationresults",
                "us-core-smokingstatus",
                "us-core-careplan",
                "us-core-organization",
                "us-core-patient"}));
        codegenMojo.setConfigFilePath("D:/tutorial/LogicalViewGenerator/fhir-profile-codegen-plugin/src/main/resources/generation-plan-stu3.xml");

        codegenMojo.execute();
    }
}
