HTML
<template>
  <div>
    <h1 class="zoomInUp white-text">RealEstateApp</h1>
    <p class="zoomInUp white-text" ref="textElement">
    </p>
  </div>
</template>

<script> //Make the paragraph a typeOut
console.log(localStorage.getItem('userData'));
</script>

<style>
.zoomInUp {
  animation: zoomInUp 1s ease forwards;
}

.dark-text {
  text-align: center;
  color: #333; /* Dark text color */
}

@keyframes zoomInUp {
  from {
    opacity: 0;
    transform: translateY(-150px);
  }
  to {
    opacity: 1;
    transform: translateY(-200px);
  }
}
</style>

  