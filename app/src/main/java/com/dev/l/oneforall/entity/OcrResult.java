package com.dev.l.oneforall.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OcrResult {

    /**
     * log_id : 1391111886920474519
     * direction : 0
     * words_result_num : 1
     * words_result : [{"vertexes_location":[{"y":377,"x":547},{"y":377,"x":628},{"y":434,"x":628},{"y":434,"x":547}],"chars":[{"char":"○","location":{"width":42,"top":380,"height":52,"left":552}}],"min_finegrained_vertexes_location":[{"y":377,"x":547},{"y":377,"x":628},{"y":434,"x":628},{"y":434,"x":547}],"finegrained_vertexes_location":[{"y":377,"x":547},{"y":377,"x":598},{"y":377,"x":628},{"y":403,"x":628},{"y":429,"x":628},{"y":434,"x":628},{"y":434,"x":576},{"y":434,"x":547},{"y":408,"x":547},{"y":382,"x":547}],"location":{"width":83,"top":377,"height":59,"left":547},"words":"○"}]
     */

    private long log_id;
    private int direction;
    private int words_result_num;
    private List<WordsResultBean> words_result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {
        /**
         * vertexes_location : [{"y":377,"x":547},{"y":377,"x":628},{"y":434,"x":628},{"y":434,"x":547}]
         * chars : [{"char":"○","location":{"width":42,"top":380,"height":52,"left":552}}]
         * min_finegrained_vertexes_location : [{"y":377,"x":547},{"y":377,"x":628},{"y":434,"x":628},{"y":434,"x":547}]
         * finegrained_vertexes_location : [{"y":377,"x":547},{"y":377,"x":598},{"y":377,"x":628},{"y":403,"x":628},{"y":429,"x":628},{"y":434,"x":628},{"y":434,"x":576},{"y":434,"x":547},{"y":408,"x":547},{"y":382,"x":547}]
         * location : {"width":83,"top":377,"height":59,"left":547}
         * words : ○
         */

        private LocationBean location;
        private String words;
        private List<VertexesLocationBean> vertexes_location;
        private List<CharsBean> chars;
        private List<MinFinegrainedVertexesLocationBean> min_finegrained_vertexes_location;
        private List<FinegrainedVertexesLocationBean> finegrained_vertexes_location;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public List<VertexesLocationBean> getVertexes_location() {
            return vertexes_location;
        }

        public void setVertexes_location(List<VertexesLocationBean> vertexes_location) {
            this.vertexes_location = vertexes_location;
        }

        public List<CharsBean> getChars() {
            return chars;
        }

        public void setChars(List<CharsBean> chars) {
            this.chars = chars;
        }

        public List<MinFinegrainedVertexesLocationBean> getMin_finegrained_vertexes_location() {
            return min_finegrained_vertexes_location;
        }

        public void setMin_finegrained_vertexes_location(List<MinFinegrainedVertexesLocationBean> min_finegrained_vertexes_location) {
            this.min_finegrained_vertexes_location = min_finegrained_vertexes_location;
        }

        public List<FinegrainedVertexesLocationBean> getFinegrained_vertexes_location() {
            return finegrained_vertexes_location;
        }

        public void setFinegrained_vertexes_location(List<FinegrainedVertexesLocationBean> finegrained_vertexes_location) {
            this.finegrained_vertexes_location = finegrained_vertexes_location;
        }

        public static class LocationBean {
            /**
             * width : 83
             * top : 377
             * height : 59
             * left : 547
             */

            private int width;
            private int top;
            private int height;
            private int left;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }
        }

        public static class VertexesLocationBean {
            /**
             * y : 377
             * x : 547
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class CharsBean {
            /**
             * char : ○
             * location : {"width":42,"top":380,"height":52,"left":552}
             */

            @SerializedName("char")
            private String charX;
            private LocationBeanX location;

            public String getCharX() {
                return charX;
            }

            public void setCharX(String charX) {
                this.charX = charX;
            }

            public LocationBeanX getLocation() {
                return location;
            }

            public void setLocation(LocationBeanX location) {
                this.location = location;
            }

            public static class LocationBeanX {
                /**
                 * width : 42
                 * top : 380
                 * height : 52
                 * left : 552
                 */

                private int width;
                private int top;
                private int height;
                private int left;

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }
            }
        }

        public static class MinFinegrainedVertexesLocationBean {
            /**
             * y : 377
             * x : 547
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }

        public static class FinegrainedVertexesLocationBean {
            /**
             * y : 377
             * x : 547
             */

            private int y;
            private int x;

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }
        }
    }
}
