/*
 * Copyright (c) Open Source Strategies, Inc.
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.notes.services;

import org.opentaps.core.service.ServiceInput;

public class GetNotesServiceInput implements ServiceInput {

    private Long fromSequence;
    private Integer numberOfNotes;
    private Integer orderDirection;

    public Long getFromSequence() { return fromSequence; }
    public void setFromSequence(Long fromSequence) { this.fromSequence = fromSequence; }
    public Integer getNumberOfNotes() { return numberOfNotes; }
    public void setNumberOfNotes(Integer numberOfNotes) { this.numberOfNotes = numberOfNotes; }
    public Integer getOrderDirection() { return orderDirection; }
    public void setOrderDirection(Integer orderDirection) { this.orderDirection = orderDirection; }
}
