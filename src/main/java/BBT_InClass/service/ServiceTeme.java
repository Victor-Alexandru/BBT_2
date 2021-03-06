package BBT_InClass.service;


import BBT_InClass.domain.Teme;
import BBT_InClass.repository.TemeRepo;

public class ServiceTeme {
    private TemeRepo rep;

    public ServiceTeme(TemeRepo rep) {
        this.rep = rep;
    }

    /***
     * Adauga tema
     * @param s
     * @return tema adaugata
     */
    public Teme add(Teme s) {
        return rep.save(s);
    }

    /***
     * sterge tema
     * @param id
     * @return tema stearsa
     */
    public Teme del(Integer id) {
        return rep.delete(id);
    }

    /***
     * Modifica tema
     * @param s
     * @return tema modificata
     */
    public Teme mod(Teme s) {
        return rep.update(s);
    }

    /***
     * Cauta tema dupa id
     * @param id
     * @return tema gasita
     */
    public Teme find(Integer id) {
        return rep.findOne(id);
    }

    /***
     * @return temele
     */
    public Iterable<Teme> all() {
        return rep.findAll();
    }

    public Integer size() {
        return rep.size();
    }
}

