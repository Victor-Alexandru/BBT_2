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
        Integer expected = 12;
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
        Integer expectedHere = 12;
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
        Assert.assertEquals(temeService.find(2).getID(), t2.getID());

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
        Assert.assertEquals(n2,null);

    }

    @Test
    public void testBigBang() {
//        addStudentsTestTwo();
        Integer expectedHere = 12;
        Assert.assertEquals(stdService.getSize(), expectedHere);
        addTemeTwo();
        Teme t1 = temeService.find(1);
        expectedHere = 1;
        Assert.assertEquals(t1.getID(), expectedHere);
        testAddGrade();



    }

    @Test
    public void bbtTestForAddStudent(){
        try{
            Student s1 = new Student("0", "Name", 931, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "ID invalid");
        }

        Student s1 = new Student("1", "Name", 931, "a@a.a", "Profesor");
        stdService.add(s1);
        Student s2 = new Student("2", "Name", 931, "a@a.a", "Profesor");
        stdService.add(s2);

        Integer result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try{
            Student s3 = new Student("3", "Name", 110, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Grupa invalid");
        }

        Student s4 = new Student("4", "Name", 111, "a@a.a", "Profesor");
        stdService.add(s4);
        Student s5 = new Student("5", "Name", 112, "a@a.a", "Profesor");
        stdService.add(s5);

        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try{
            Student s6 = new Student("6", "Name", 937, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Grupa invalid");
        }

        Student s7 = new Student("7", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s7);
        Student s8 = new Student("8", "Name", 935, "a@a.a", "Profesor");
        stdService.add(s8);

        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        Integer s9 = 7;

        Student s10 = new Student("9", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s10);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try {
            Student s11 = new Student("ew", "Name", 936, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "ID invalid");
        }

        Student s12 = new Student("10", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s12);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try {
            Student s13 = new Student("11", "123", 936, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Nume invalid");
        }

        Student s14 = new Student("12", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s14);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try {
            Student s15 = new Student("13", "Name", -1, "a@a.a", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Grupa invalid");
        }

        Student s16 = new Student("14", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s16);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        try {
            Student s17 = new Student("15", "Name", 936, "123", "Profesor");
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Email invalid");
        }

        Student s18 = new Student("16", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s18);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        Student s19 = new Student("16", "Name", 936, "a@a.a", "Profesor");
        stdService.add(s19);
        result = 12;
        Assert.assertEquals(stdService.getSize(), result);
    }

    @Test
    public void wbtForAddAssigment(){
        try {
            Teme teme1 = new Teme(-300, "description", 1, 10);
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "ID invalid");
        }

        Teme teme2 = new Teme(1, "description", 2, 10);
        temeService.add(teme2);
        Teme teme3 = new Teme(1, "description", 2, 10);
        temeService.add(teme3);

        Integer result=4;
        Assert.assertEquals(temeService.size(),result);

        try {
            Teme teme4 = new Teme(2, "description", 500, 10);
        }
        catch(Exception e){
            Assert.assertEquals(e.getMessage(), "Saptamana in care tema a fost primita este invalida");
        }

        try {
            Teme teme5 = new Teme(3, "description", 2, 15);
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Deadline invalid");
        }

        try {
            Teme teme6 = new Teme(4, "description", 1, 10);
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Saptamana in care tema a fost primita este invalida");
        }

        try {
            Teme teme7 = new Teme(5, "description", 2, 1);
        }
        catch (Exception e){
            Assert.assertEquals(e.getMessage(), "Deadline invalid");
        }



        Teme teme8 = new Teme(6, "description", 2, 10);
        temeService.add(teme8);
        Teme teme9 = new Teme(7, "description", 2, 5);
        temeService.add(teme9);

        result=4;
        Assert.assertEquals(temeService.size(),result);
    }

    @Test
    public void testAddStudent(){
        Student s1 = new Student("1", "Name", 931, "a@a.a", "Profesor");
        stdService.add(s1);

        Integer result = 12;
        Assert.assertEquals(stdService.getSize(), result);
    }

    @Test
    public void testAddStudentAndAddAssigment(){
        Student s1 = new Student("2", "Name", 931, "a@a.a", "Profesor");

        Teme teme = new Teme(2, "description", 2, 10);

        stdService.add(s1);
        temeService.add(teme);

        Integer result = 12;
        Assert.assertEquals(stdService.getSize(), result);
        result=4;
        Assert.assertEquals(temeService.size(),result);
    }

    @Test
    public void testAddStudentAssigmentAndGrade(){
        Student s1 = new Student("3", "Name", 931, "a@a.a", "Profesor");

        Teme teme = new Teme(1, "description", 2, 10);

        Nota nota = new Nota(new Map.Entry<String, Integer>() {
            @Override
            public String getKey() {
                return "1";
            }

            @Override
            public Integer getValue() {
                return 8;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        }, s1, teme, 9, 4);

        stdService.add(s1);
        temeService.add(teme);
        noteService.add(nota, "text");


        Integer result = 12;
        Assert.assertEquals(stdService.getSize(), result);

        result=4;
        Assert.assertEquals(temeService.size(),result);

        result=1;
        Assert.assertEquals(noteService.getSize(), result);

    }


    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
