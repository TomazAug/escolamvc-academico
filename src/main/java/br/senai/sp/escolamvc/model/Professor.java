package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;

@DiscriminatorValue(value = "P")
@Entity
public class Professor extends Pessoa {}