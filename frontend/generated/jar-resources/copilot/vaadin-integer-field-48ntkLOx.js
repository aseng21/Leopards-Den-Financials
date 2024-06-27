import { inputFieldProperties as t, labelProperties as i, helperTextProperties as a, errorMessageProperties as o } from "./vaadin-text-field-AO53X0oA.js";
import { N as e } from "./copilot-tF9CaZ8e.js";
import { standardButtonProperties as p } from "./vaadin-button-QNnI89Z-.js";
const d = {
  tagName: "vaadin-integer-field",
  displayName: "Integer Field",
  elements: [
    {
      selector: "vaadin-integer-field::part(input-field)",
      displayName: "Input field",
      properties: t
    },
    {
      selector: "vaadin-integer-field::part(label)",
      displayName: "Label",
      properties: i
    },
    {
      selector: "vaadin-integer-field::part(helper-text)",
      displayName: "Helper text",
      properties: a
    },
    {
      selector: "vaadin-integer-field::part(error-message)",
      displayName: "Error message",
      properties: o
    },
    {
      selector: "vaadin-integer-field::part(clear-button)",
      displayName: "Clear button",
      properties: p
    },
    {
      selector: "vaadin-integer-field::part(decrease-button)",
      displayName: "Decrease button",
      properties: [e.iconColor, e.iconSize]
    },
    {
      selector: "vaadin-integer-field::part(increase-button)",
      displayName: "Increase button",
      properties: [e.iconColor, e.iconSize]
    }
  ],
  setupElement(r) {
    r.stepButtonsVisible = !0;
  }
};
export {
  d as default
};
