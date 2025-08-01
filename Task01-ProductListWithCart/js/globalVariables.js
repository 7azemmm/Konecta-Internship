export let products = [];
export let cart = [];
export const discountCodes = ['apply10', 'apply25'];
export let appliedDiscount = 0;

export function setProducts(value) {
  products = value;
}
export function setCart(value) {
  cart = value;
}
export function setAppliedDiscount(value) {
  appliedDiscount = value;
}
