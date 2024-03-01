package tn.esprit.ProjetSpring.Services;

import tn.esprit.ProjetSpring.entities.Specialite;

import java.util.List;

public interface ISpecialiteService {
    Specialite add(Specialite specialite, String nomUni);
    Specialite getById(Long id);
    List<Specialite> getAll();
    void delete(long id);
    Specialite update(Specialite specialite,String nomUni);
}
