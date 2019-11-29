package br.org.otus.rest;

import br.org.otus.examUploader.ExamResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ccem.otus.model.survey.activity.SurveyActivity;

public class Response {

	private Object data;

	public Object getData() {
		return data;
	}

	public Response setData(Object data) {
		this.data = data;
		return this;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String toJson(GsonBuilder builder){
		return builder.create().toJson(this);
	}

	public String toSurveyJson() {
		return SurveyActivity.getGsonBuilder().create().toJson(this);
	}

	public String toExamJson(){
		return ExamResult.getGsonBuilder().create().toJson(this);
	}

	public Response buildSuccess(Object data) {
		this.data = data;
		return this;
	}

	public Response buildSuccess() {
		this.data = Boolean.TRUE;
		return this;
	}

}