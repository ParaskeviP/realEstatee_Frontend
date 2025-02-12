HTML
<template>

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
  color: #333;
  display: none;
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