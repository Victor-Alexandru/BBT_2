package BBT_InClass.service;


import BBT_InClass.domain.Nota;
import BBT_InClass.repository.NoteRepo;

public class ServiceNote {
    private NoteRepo rep;
    public ServiceNote(NoteRepo rep){this.rep=rep;}
    /**
     * Adauga Nota
     * Returneaza Nota adaugata*/
    public Nota add(Nota s, String fd){
        return rep.save(s,fd);
    }

    public Iterable<Nota> all(){
        return rep.findAll();
    }
}

