package BBT_InClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import BBT_InClass.domain.Nota;
import BBT_InClass.domain.Student;
import BBT_InClass.domain.Teme;
import BBT_InClass.repository.NoteRepo;
import BBT_InClass.repository.StudentRepo;
import BBT_InClass.repository.TemeRepo;
import BBT_InClass.service.ServiceNote;
import BBT_InClass.service.ServiceStudent;
import BBT_InClass.service.ServiceTeme;
import BBT_InClass.validator.NotaValidator;
import BBT_InClass.validator.StudentValidator;
import BBT_InClass.validator.TemeValidator;
import BBT_InClass.validator.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private ServiceStudent stdService;
    private ServiceTeme temeService;
    private ServiceNote noteService;

    /**
     * Rigorous Test :-)
     */
    @Before
    public void setUp() {
        StudentRepo studentRepo = new StudentRepo(new StudentValidator(), "studenti.xml");
        stdService = new ServiceStudent(studentRepo);
        TemeRepo temeRepo = new TemeRepo(new TemeValidator(), "teme.xml");
        temeService = new ServiceTeme(temeRepo);
        NoteRepo noteRepo = new NoteRepo(new NotaValidator(), "note.xml");
        noteService = new ServiceNote(noteRepo);
    }

    @Test
    public void addStudentsTestOne() {


        Student s1 = new Student("1", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        Student s2 = new Student("2", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        //students already exists in the xml so it will be 3
        stdService.add(s1);
        stdService.add(s2);
        Integer expected = 3;
        Assert.assertEquals(stdService.getSize(), expected);
    }

    @Test
    public void addStudentsTestTwo() {
        Student s1 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        Student s2 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        Student s3 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        Student s4 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        Student s5 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        stdService.add(s1);
        stdService.add(s2);
        stdService.add(s3);
        stdService.add(s4);
        stdService.add(s5);
        Integer expectedHere = 3;
        // 3  = 2 from the file and 1 added with id 3
        Assert.assertEquals(stdService.getSize(), expectedHere);
    }


    @Test
    public void addTemeOne() {

        Teme t1 = new Teme(1, "wwww", 1, 2);
        temeService.add(t1);
        try {
            Teme t4 = new Teme(1, "wwww", 1, 2);
            t4.setID(-400);
            temeService.add(t4);
        } catch (ValidationException e) {
            Integer expectedHere = 1;
            Assert.assertEquals(t1.getID(), expectedHere);
        }

    }

    @Test
    public void addTemeTwo() {
        Teme t2 = new Teme(2, "wwww", 1, 2);
        temeService.add(t2);
        temeService.add(t2);
        temeService.add(t2);
        temeService.add(t2);
        Integer expectedHere = 2;
        Assert.assertEquals(t2.getID(), expectedHere);
        Integer expectedHereTwo = 3;
        try {
            Teme t3 = new Teme(2, "wwww", 50, 15);
            //Invalid tema

        } catch (ValidationException e) {
            Teme t4 = new Teme(2, "wwww", 1, 2);
            Assert.assertEquals(t4.getID(), expectedHereTwo);
        }

    }

    @Test
    public void testDelete() {
        this.setUp();
        Teme t2 = new Teme(2, "wwww", 1, 2);
        temeService.del(2);
        temeService.add(t2);
        Assert.assertEquals(temeService.find(t2.getID()), t2);

        temeService.del(t2.getID());
        Assert.assertNull(temeService.find(2));

        Student s1 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        stdService.del("3");
        stdService.add(s1);
        Assert.assertEquals(stdService.find(s1.getID()), s1);

        stdService.del(s1.getID());
        Assert.assertNull(stdService.find(s1.getID()));
    }

    @Test
    public void testUpdate() {
        this.setUp();
        Teme t2 = new Teme(2, "wwww", 1, 2);
        temeService.add(t2);
        Assert.assertEquals(temeService.find(t2.getID()).getID(), t2.getID());

        t2 = new Teme(2, "wwww2", 12, 13);
        temeService.mod(t2);
        Assert.assertEquals(temeService.find(2), t2);

        Student s1 = new Student("3", "Antoniu", 931, "a@a.ro", "Artur Molnar");
        stdService.add(s1);
        Assert.assertEquals(stdService.find(s1.getID()).getID(), s1.getID());

        s1 = new Student("3", "Antoniuu", 932, "a2@a2.ro", "Artur Molnarr");
        stdService.mod(s1);
        Assert.assertEquals(stdService.find(s1.getID()).getID(), s1.getID());
    }

    @Test
    public void testAddGrade() {
        this.setUp();
        Map.Entry<String, Integer> nid = new AbstractMap.SimpleEntry<String, Integer>("1", 1);
        Nota n1 = new Nota(nid, this.stdService.find("1"), this.temeService.find(1), 8, 5);
        Nota n2 = this.noteService.add(n1, "all good");
        Integer expectedHere = 1;
        Assert.assertEquals(n2,null);

    }

    @Test
    public void testBigBang() {
        addStudentsTestTwo();
        Integer expectedHere = 3;
        Assert.assertEquals(stdService.getSize(), expectedHere);
        addTemeTwo();
        Teme t1 = temeService.find(1);
        expectedHere = 1;
        Assert.assertEquals(t1.getID(), expectedHere);
        testAddGrade();



    }


    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
