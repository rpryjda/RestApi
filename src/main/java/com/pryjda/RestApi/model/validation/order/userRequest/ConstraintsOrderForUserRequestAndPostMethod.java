package com.pryjda.RestApi.model.validation.order.userRequest;

import javax.validation.GroupSequence;

@GroupSequence({CreatingUserStepNo1.class, CreatingUserStepNo2.class, CreatingUserStepNo3.class})
public interface ConstraintsOrderForUserRequestAndPostMethod {
}
