package br.org.otus.laboratory.project.exam.upload;

import org.bson.types.ObjectId;

public class ExamResult {

    private ObjectId id;
    private ObjectId examId;
    private String aliquotCode;

    private String name;
    private String label;
    private String solicitationDate;
    private String collectionDate;
    private String releaseDate;
    private String fieldCenter;
}

