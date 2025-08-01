import { discountCodes, setAppliedDiscount } from '../globalVariables.js';
import { discountCodeStatus } from '../domElements.js';
import { updateCartDisplay } from './cartService.js';

export function checkDiscount() {
  const code = document.getElementById('discountCode').value;

  if (code === discountCodes[0]) {
    setAppliedDiscount(0.10);
    discountCodeStatus.innerText = "the discount code 10% is correct";
    discountCodeStatus.style.color = "green";
  } else if (code === discountCodes[1]) {
    setAppliedDiscount(0.25);
    discountCodeStatus.innerText = "the discount code 25% is correct";
    discountCodeStatus.style.color = "green";
  } else {
    setAppliedDiscount(0);
    discountCodeStatus.innerText = "the discount code is invalid";
    discountCodeStatus.style.color = "red";
  }

  updateCartDisplay();
}
