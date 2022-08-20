<template>
  <div
      style="background-color: rgba(0, 0, 0, .5);"
      class="fixed top-0 left-0 w-full h-full flex items-center shadow-lg overflow-y-auto z-50"
      tabindex="0"
      v-show="value"
      id="updateComment">
    <div class="container mx-auto lg:px-32 rounded-lg overflow-y-auto">
      <div class="bg-gray-900 rounded">
        <div class="flex justify-end pr-4 pt-2">
          <button @click="close()" class="text-3xl leading-none hover:text-gray-300">&times;</button>
        </div>
        <div class="modal-body px-8 py-8">
          <div class="responsive-container overflow-hidden relative">
            <add-comment @commentUpdated="updatedComment()" mode="edit" :comment-db="this.commentDb"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AddComment from "@/components/comments/AddComment";

export default {
  name: "UpdateCommentModal",
  components: {AddComment},
  props: {
    value: {
      required: true,
    },
    commentDb: {
      required: true,
    },
  },
  methods: {
    close() {
      this.$emit("input", !this.value);
    },
    updatedComment() {
      this.$emit('commentUpdated2');
    }
  },
}
</script>

<style scoped>
#updateComment {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  /*color: #2c3e50;*/
  color: whitesmoke;
  margin-top: 60px;
}

body {
  font-family: Menlo, Monaco, "Courier New", monospace;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  margin: 0;
}

pre {
  overflow: auto;
}
pre .string {
  color: #885800;
}
pre .number {
  color: blue;
}
pre .boolean {
  color: magenta;
}
pre .null {
  color: red;
}
pre .key {
  color: green;
}
</style>