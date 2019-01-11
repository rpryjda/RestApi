package com.pryjda.RestApi.model.validation.order.userRequest;

import javax.validation.GroupSequence;

@GroupSequence({UpdatingUserStepNo1.class, UpdatingUserStepNo2.class, CreatingUserStepNo3.class})
public interface ConstraintsOrderForUserRequestAndPutMethod {
}
