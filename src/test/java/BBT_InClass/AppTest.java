package BBT_InClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import BBT_InClass.domain.Student;
import BBT_InClass.repository.StudentRepo;
import BBT_InClass.service.ServiceStudent;
import BBT_InClass.validator.StudentValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private ServiceStudent stdService;
    /**
     * Rigorous Test :-)
     */
    @Before
    public void setUp() {
        StudentRepo studentRepo = new StudentRepo(new StudentValidator(),"studenti.xml");
        stdService = new ServiceStudent(studentRepo);
    }

    @Test
    public void addStudentsTestOne() {

        Student s1 = new Student("1", "Antoniu", 931, "a@a.ro","Artur Molnar");
        Student s2 = new Student("2", "Antoniu", 931, "a@a.ro","Artur Molnar");
        //students already exists in the xml so it will be 3
        stdService.add(s1);
        stdService.add(s2);
        Integer expected = 3;
        Assert.assertEquals(stdService.getSize(),expected);
    }
    @Test
    public void addStudentsTestTwo()  {
        Student s1 = new Student("3", "Antoniu", 931, "a@a.ro","Artur Molnar");
        Student s2 = new Student("3", "Antoniu", 931, "a@a.ro","Artur Molnar");
        Student s3 = new Student("3", "Antoniu", 931, "a@a.ro","Artur Molnar");
        Student s4 = new Student("3", "Antoniu", 931, "a@a.ro","Artur Molnar");
        Student s5 = new Student("3", "Antoniu", 931, "a@a.ro","Artur Molnar");
        stdService.add(s1);
        stdService.add(s2);
        stdService.add(s3);
        stdService.add(s4);
        stdService.add(s5);
        Integer expectedHere = 3;
        // 3  = 2 from the file and 1 added with id 3
        Assert.assertEquals(stdService.getSize(),expectedHere);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
