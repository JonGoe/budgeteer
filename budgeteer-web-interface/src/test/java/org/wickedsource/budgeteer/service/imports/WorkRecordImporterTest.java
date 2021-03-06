package org.wickedsource.budgeteer.service.imports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.wickedsource.budgeteer.importer.aproda.AprodaWorkRecordsImporter;
import org.wickedsource.budgeteer.imports.api.ImportException;
import org.wickedsource.budgeteer.imports.api.ImportFile;
import org.wickedsource.budgeteer.imports.api.InvalidFileFormatException;
import org.wickedsource.budgeteer.persistence.project.ProjectEntity;
import org.wickedsource.budgeteer.persistence.project.ProjectRepository;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring-service.xml", "classpath:spring-repository-mock.xml"})
public class WorkRecordImporterTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ImportService importService;

    private void doImport() throws ImportException, InvalidFileFormatException {
        List<ImportFile> importFiles = new ArrayList<ImportFile>();
        importFiles.add(new ImportFile("file1", getClass().getResourceAsStream("testReport3.xlsx")));
        importService.doImport(1l, new AprodaWorkRecordsImporter(), importFiles);
    }

    @Test
    @DatabaseSetup("doImportWithEmptyDatabase.xml")
    @DatabaseTearDown(value = "doImportWithEmptyDatabase.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetSkippedRecordsNoSkippedRecords() throws Exception {
        Mockito.when(projectRepository.findOne(Mockito.anyLong())).thenReturn(new ProjectEntity());
        doImport();
        List<List<String>> skippedRecords = importService.getSkippedRecords();
        Assert.assertEquals(3, skippedRecords.size());
    }

    @Test
    @DatabaseSetup("doImportWithEmptyDatabase.xml")
    @DatabaseTearDown(value = "doImportWithEmptyDatabase.xml", type = DatabaseOperation.DELETE_ALL)
    public void testGetSkippedRecordsSomeSkippedRecords() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
        ProjectEntity project = new ProjectEntity();
        project.setProjectStart(formatter.parse("02.01.2014"));
        project.setProjectEnd(formatter.parse("12.01.2014"));
        Mockito.when(projectRepository.findOne(Mockito.anyLong())).thenReturn(project);
        doImport();
        List<List<String>> skippedRecords = importService.getSkippedRecords();
        Assert.assertEquals(10, skippedRecords.size());
    }
}
