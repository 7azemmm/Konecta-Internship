import { cart, appliedDiscount } from '../globalVariables.js';

export async function generateCartPDF() {
  const { jsPDF } = window.jspdf;
  const doc = new jsPDF();

  let distance = 10;
  doc.setFontSize(16);
  doc.text("Order Receipt", 80, distance);
  distance += 10;

  doc.setFontSize(12);
  cart.forEach((item, index) => {
    doc.text(`${index + 1}. ${item.name}`, 10, distance);
    doc.text(`$${item.price.toFixed(2)} x ${item.quantity}`, 100, distance);
    distance += 8;
  });

  let subtotal = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  let discountAmount = subtotal * appliedDiscount;
  let total = subtotal - discountAmount;

  doc.text(`Subtotal: $${subtotal.toFixed(2)}`, 10, distance += 5);
  if (appliedDiscount > 0) {
    doc.text(`Discount (${appliedDiscount * 100}%): -$${discountAmount.toFixed(2)}`, 10, distance += 7);
  }
  doc.setFontSize(14);
  doc.text(`Total: $${total.toFixed(2)}`, 10, distance += 7);

  const blobUrl = URL.createObjectURL(doc.output("blob"));
  window.open(blobUrl);
}
