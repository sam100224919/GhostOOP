package com.ghostoop.model;

import com.ghostoop.exceptions.InvalidOrderStateException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Represents the lifecycle states of an {@link Order}.
 * Enforces validated state transitions according to e-commerce business workflows.
 */
public enum OrderStatus {
    PENDING {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.unmodifiableSet(EnumSet.of(PAID, CANCELLED));
        }
    },
    PAID {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.unmodifiableSet(EnumSet.of(PROCESSING, CANCELLED));
        }
    },
    PROCESSING {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.unmodifiableSet(EnumSet.of(SHIPPED, CANCELLED));
        }
    },
    SHIPPED {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.unmodifiableSet(EnumSet.of(DELIVERED));
        }
    },
    DELIVERED {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.emptySet();
        }
    },
    CANCELLED {
        @Override
        public Set<OrderStatus> allowedNextStates() {
            return Collections.emptySet();
        }
    };

    /**
     * Returns the set of valid states that can directly follow this state.
     *
     * @return set of allowable next statuses
     */
    public abstract Set<OrderStatus> allowedNextStates();

    /**
     * Validates whether transitioning to the target state is permissible.
     *
     * @param targetStatus the desired next order status
     * @throws InvalidOrderStateException if the transition is illegal or target is null
     */
    public void validateTransition(OrderStatus targetStatus) {
        if (targetStatus == null) {
            throw new InvalidOrderStateException("Target order status cannot be null.");
        }
        if (!allowedNextStates().contains(targetStatus)) {
            throw new InvalidOrderStateException(String.format(
                    "Illegal state transition from %s to %s.", this.name(), targetStatus.name()));
        }
    }
}
