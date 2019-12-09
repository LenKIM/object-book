public enum SortType {
    TITLE_DESC {
        @Override
        int compare(Task a, Task b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(Task a, Task b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(Task a, Task b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    TITLE_ASC {
        @Override
        int compare(Task a, Task b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    };

    abstract int compare(Task a, Task b);
}
