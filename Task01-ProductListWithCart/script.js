let products = [];

// DOM elements
const productsGrid = document.getElementById('productsGrid');

async function init() {
  try {
    await loadProducts();
    renderProducts();
  
  } catch (error) {
    console.error('Error initializing app:', error);
  }
}

// Load products from data.json
async function loadProducts() {
  try {
    const response = await fetch('./data.json');
    products = await response.json();
    console.log(products)
  } catch (error) {
    console.error('Error loading products:', error);
    throw error;
  }
}

// Render products grid
function renderProducts() {
  productsGrid.innerHTML = products.map(product => `
    <article class="product-card">
      <img 
        src="${product.image.desktop}" 
        alt="${product.name}"
        class="product-card-image"
        loading="lazy"
      >
      <div class="product-card-content">
        <h3 class="product-card-name">${product.name}</h3>
        <p class="product-card-category">${product.category}</p>
        <p class="product-card-price">$${product.price.toFixed(2)}</p>
        <button 
          class="product-card-button"
          onclick="addToCart('${product.name}')"
        >
          <img src="./assets/images/icon-add-to-cart.svg" alt="">
          Add to Cart
        </button>
      </div>
    </article>
  `).join('');
}



document.addEventListener('DOMContentLoaded', () => {
  init();
  
});



