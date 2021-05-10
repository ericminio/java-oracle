package ericminio.javaoracle.http;

import ericminio.javaoracle.domain.FileInfo;
import ericminio.javaoracle.domain.FileSet;
import ericminio.javaoracle.support.Stringify;
import ericminio.javaoracle.support.Unzip;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static ericminio.javaoracle.http.PostFormRequest.postForm;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class DownloadZipTest {

    Server server;
    Integer port = 8005;

    @Before
    public void start() throws IOException {
        server = new Server(port);
        server.start();
    }
    @After
    public void stop() {
        server.stop();
    }

    @Test
    public void returnsZip() throws Exception {
        FormDataSet formDataSet = new FormDataSet();
        formDataSet.add(file("package", "create-package.sql"));
        formDataSet.add(file("types", "create-types.sql"));
        formDataSet.add(new FormData("javaPackage", "examples"));
        HttpResponse response = postForm("http://localhost:" + port + "/zip", formDataSet);
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.getContentType(), equalTo("application/zip"));

        FileSet output = new Unzip().please(response.getBinaryBody());
        assertThat(output.size(), equalTo(3));
    }

    @Test
    public void zipContainsPackageClass() throws Exception {
        FormDataSet formDataSet = new FormDataSet();
        formDataSet.add(file("package", "create-package.sql"));
        formDataSet.add(file("types", "create-types.sql"));
        formDataSet.add(new FormData("javaPackage", "examples"));
        HttpResponse response = postForm("http://localhost:" + port + "/zip", formDataSet);
        FileSet output = new Unzip().please(response.getBinaryBody());
        FileInfo fileInfo = output.getByFileName("AnyPackage.java");
        assertThat(fileInfo, not(equalTo(null)));
        String code = fileInfo.getContent();

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class AnyPackage {"));
        assertThat(code, containsString("   private Connection connection;"));
        assertThat(code, containsString("   public AnyPackage(Connection connection) throws SQLException {"));
        assertThat(code, containsString("       connection.getTypeMap().put(ExampleAnyType.NAME, ExampleAnyType.class);"));

        assertThat(code, containsString("   public ExampleArrayType anyFunction()"));
        assertThat(code, containsString("       PreparedStatement statement = connection.prepareStatement(\"select any_package.any_function() from dual\");"));
        assertThat(code, containsString("       return ExampleArrayType.with((Object[]) resultSet.getArray(1).getArray());"));
    }

    @Test
    public void createsArrayTypeFile() throws Exception {
        FormDataSet formDataSet = new FormDataSet();
        formDataSet.add(file("package", "create-package.sql"));
        formDataSet.add(file("types", "create-types.sql"));
        formDataSet.add(new FormData("javaPackage", "examples"));
        HttpResponse response = postForm("http://localhost:" + port + "/zip", formDataSet);
        FileSet output = new Unzip().please(response.getBinaryBody());
        FileInfo fileInfo = output.getByFileName("ExampleArrayType.java");
        assertThat(fileInfo, not(equalTo(null)));
        String code = fileInfo.getContent();

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class ExampleArrayType {"));
    }

    @Test
    public void createsRecordTypeFile() throws Exception {
        FormDataSet formDataSet = new FormDataSet();
        formDataSet.add(file("package", "create-package.sql"));
        formDataSet.add(file("types", "create-types.sql"));
        formDataSet.add(new FormData("javaPackage", "examples"));
        HttpResponse response = postForm("http://localhost:" + port + "/zip", formDataSet);
        FileSet output = new Unzip().please(response.getBinaryBody());
        FileInfo fileInfo = output.getByFileName("ExampleAnyType.java");
        assertThat(fileInfo, not(equalTo(null)));
        String code = fileInfo.getContent();

        assertThat(code, containsString("package examples;\n\n"));
        assertThat(code, containsString("public class ExampleAnyType implements SQLData {"));
        assertThat(code, containsString("    public static final String NAME = \"EXAMPLE_ANY_TYPE\";"));
    }

    private FileFormData file(String name, String fileName) throws IOException {
        String content = new Stringify().inputStream(stream(fileName));
        return new FileFormData(name, fileName, content);
    }

    private InputStream stream(String fileName) {
        return this.getClass().getClassLoader().getResourceAsStream(fileName);
    }
}
