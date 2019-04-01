package com.wplatform.wizardcraft.attribute;// <copyright file="AttributeDefinition.cs" company="MUnique">
// Licensed under the MIT License. See LICENSE file in the project root for full license information.
// </copyright>

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinition {

    /// <summary>
    /// Gets or sets the identifier.
    /// </summary>
    public int Id;

    /// <summary>
    /// Gets or sets the designation.
    /// </summary>
    /// <value>
    /// The designation.
    /// </value>
    public String Designation;

    /// <summary>
    /// Gets or sets the description.
    /// </summary>
    /// <value>
    /// The description.
    /// </value>
    public String Description;


}
