package br.org.otus.survey.activity.activityReview;

import org.ccem.otus.model.survey.activity.activityReview.ActivityReview;
import org.ccem.otus.service.activityReview.ActivityReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ActivityReviewFacadeTest {
    private static final String ID = "{\"data\":\"5c41cab016da480065be5d3c}";
    @Mock
    private ActivityReviewService activityReviewService;
    @InjectMocks
    private ActivityReviewFacade activityReviewFacade;
    @Mock
    private ActivityReview activityReview;
    @Test
    public void method_should_verify_create_with_activityReview() {
        when(activityReviewService.create(activityReview)).thenReturn(ID);
        activityReviewFacade.create(activityReview);
        verify(activityReviewService, times(1)).create(activityReview);
    }

    @Test
    public void method_should_verify_list_with_rn() {
        when(activityReviewService.list()).thenReturn(new ArrayList<>());
        activityReviewFacade.list();
        verify(activityReviewService, times(1)).list();
    }
}
