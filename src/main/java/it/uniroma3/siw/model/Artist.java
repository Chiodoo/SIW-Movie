package it.uniroma3.siw.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Artist {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String nome;
        private String cognome;
        private LocalDate nascita;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCognome() {
                return cognome;
        }

        public void setCognome(String cognome) {
                this.cognome = cognome;
        }

        public LocalDate getNascita() {
                return nascita;
        }
        
        public void setNascita(LocalDate nascita) {
                this.nascita = nascita;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((nome == null) ? 0 : nome.hashCode());
                result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
                result = prime * result + ((nascita == null) ? 0 : nascita.hashCode());
                return result;
        }

        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                        return true;
                if (obj == null)
                        return false;
                if (getClass() != obj.getClass())
                        return false;
                Artist other = (Artist) obj;
                if (nome == null) {
                        if (other.nome != null)
                                return false;
                } else if (!nome.equals(other.nome))
                        return false;
                if (cognome == null) {
                        if (other.cognome != null)
                                return false;
                } else if (!cognome.equals(other.cognome))
                        return false;
                if (nascita == null) {
                        if (other.nascita != null)
                                return false;
                } else if (!nascita.equals(other.nascita))
                        return false;
                return true;
        }
}
