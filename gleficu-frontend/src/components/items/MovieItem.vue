<template>
  <div>
    <router-link :to="`/movie/${movie.imdbID}`">
      <img
        :src="movie.poster"
        class="hover:opacity-75 tansition easy-in-out duration-150"
      />
    </router-link>
    <h3>{{ movie.title }}</h3>
    <div class="flex">
      <svg
        class="fill-current text-yellow-500 w-4 h-4 mt-1"
        viewBox="0 0 24 24"
      >
        <g data-name="Layer 2">
          <path
            d="M17.56 21a1 1 0 01-.46-.11L12 18.22l-5.1 2.67a1 1 0 01-1.45-1.06l1-5.63-4.12-4a1 1 0 01-.25-1 1 1 0 01.81-.68l5.7-.83 2.51-5.13a1 1 0 011.8 0l2.54 5.12 5.7.83a1 1 0 01.81.68 1 1 0 01-.25 1l-4.12 4 1 5.63a1 1 0 01-.4 1 1 1 0 01-.62.18z"
            data-name="star"
          />
        </g></svg
      ><span class="ml-2"
        >{{ movie.imdbRating * 10 }}% | {{ movie.released }} </span
      ><br />
    </div>
    <span class="text-sm text-gray-500">
      {{ movie.genre }}
    </span>
  </div>
</template>

<script>
export default {
  props: {
    movie: {
      required: true,
    },
  },

  methods: {
  },
};
</script>

<style></style>
