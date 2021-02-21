/*
 * Copyright 2014 Bang Jun-young
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.flow9.searchjaum.lib;

import java.util.Iterator;

/**
 * 한글 음절 근사 매칭 클래스.
 * <p>
 * 이 클래스는 문자 수준에서의 음절 근사 매칭 API를 포함하고 있는
 * {@link KoreanCharApproxMatcher}의 상위 서비스로 문자열 수준에서의 음절 근사 매칭
 * API를 포함하고 있다.
 * <p>
 * 한글 음절 근사 매칭은 두 개의 한글 음절을 비교할 때 음절을 이루는 자모의 일부만
 * 일치해도 두 음절이 같은 것으로 간주하는 매칭 방법이다. 예를 들어 일반적인 문자
 * 비교에서 {@code '밝'}은 {@code 'ㅂ'}, {@code '바'}, {@code '발'}과 다른 문자로
 * 간주되지만 음절 근사 매칭에서는 {@code '밝'}에 {@code 'ㅂ'}, {@code '바'},
 * {@code '발'}이 모두 부합하는 것으로 간주한다.
 * <p>
 * 단, 여기서 주의할 점은 자모의 개수가 다른 두 음절간 비교시 교환성이 성립하지
 * 않는다는 것이다. 예를 들어 {@code '발'}은 {@code '밝'}에 부합하지만
 * {@code '밝'}은 {@code '발'}에 부합하지 않는다. 즉, 자모의 개수가 더 많은 음절에
 * 대해 자모의 개수가 더 적은 음절을 비교하는 쪽으로 매칭이 이루어져야 한다는 뜻이다.
 * <p>
 * 매칭에 사용할 자모로 Unicode Hangul Jamo와 Hangul Compatibility Jamo를 모두
 * 지원한다.
 *
 * @author 방준영 &lt;bang.junyoung@gmail.com&gt;
 */
public final class KoreanTextMatcher {

    private final String _pattern;
    private final boolean _foundStartAnchor, _foundEndAnchor;

    /**
     * {@link KoreanTextMatcher} 클래스의 새 인스턴스를 초기화한다.
     *
     * 정규식 앵커 {@code ^}와 {@code $}를 사용하여 {@code pattern}의 위치를
     * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
     *
     * @param pattern 검색할 패턴
     * @throws IllegalArgumentException {@code pattern}이 {@code null}일 때.
     */
    public KoreanTextMatcher(String pattern) {
        if (pattern == null)
            throw new IllegalArgumentException("pattern: null");

        if (pattern.length() == 0) {
            _foundStartAnchor = _foundEndAnchor = false;
            _pattern = pattern;
        } else {
            _foundStartAnchor = pattern.charAt(0) == '^';
            _foundEndAnchor = pattern.charAt(pattern.length() - 1) == '$';
            _pattern = stripAnchors(pattern);
        }
    }

    /**
     * 주어진 {@code text}에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * {@code pattern}의 첫번째 출현을 찾는다.
     *
     * {@code text} 내 검색 시작 위치를 지정하려면 {@link #match(String, int)}를 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 {@code true}일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException {@code text}가 {@code null}일 때.
     */
    public KoreanTextMatch match(String text) {
        return match(text, 0);
    }

    /**
     * 주어진 {@code text}에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * {@code pattern}의 첫번째 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 {@code text} 내 위치
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 {@code true}일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException {@code text}가 {@code null}일 때,
     *         또는 {@code startIndex}가 {@code 0}보다 작거나
     *         {@link KoreanTextMatch#length() text.length()}보다 클 때.
     */
    public KoreanTextMatch match(String text, int startIndex) {
        if (text == null)
            throw new IllegalArgumentException("text: null");
        if (startIndex < 0)
            throw new IllegalArgumentException("startIndex: " + startIndex + " < 0");
        if (startIndex > text.length())
            throw new IllegalArgumentException(
                String.format("startIndex: %d > text.length(): %d", startIndex, text.length()));

        //
        // Optimization: narrow the range of text to be matched for pattern.
        //
        long textRange = getTextRange(text, _pattern.length(), startIndex);
        if (textRange == -1)
            return KoreanTextMatch.EMPTY;
        // textRange is a tuple of (int startIndex, int length).
        startIndex = (int)(textRange >> 32);
        int length = (int)(textRange & 0xFFFFFFF);
        if (length == 0)
            return new KoreanTextMatch(this, text, 0, 0);

        return match(text, startIndex, length);
    }

    /**
     * 주어진 {@code text}에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * {@code pattern}의 모든 출현을 찾는다.
     *
     * {@code text} 내 검색 시작 위치를 지정하려면 {@link #matches(String, int)}를
     * 사용한다.
     *
     * @param text 검색 대상 문자열
     * @return 검색 결과를 담은 {@code Iterable<KoreanTextMatch>} 인스턴스.
     *         찾은 것이 없으면 빈 리스트를 리턴한다. 
     * @throws IllegalArgumentException {@code text}가 {@code null}일 때.
     */
    public Iterable<KoreanTextMatch> matches(String text) {
        return matches(text, 0);
    }

    /**
     * 주어진 {@code text}에 대해 {@link #KoreanTextMatcher(String)}에서 지정해 둔
     * {@code pattern}의 모든 출현을 찾는다.
     *
     * @param text 검색 대상 문자열
     * @param startIndex 검색을 시작할 {@code text} 내 위치
     * @return 검색 결과를 담은 {@code Iterable<KoreanTextMatch>} 인스턴스.
     * @throws IllegalArgumentException {@code text}가 {@code null}일 때,
     *         또는 {@code startIndex}가 {@code 0}보다 작거나
     *         {@link KoreanTextMatch#length() text.length()}보다 클 때.
     */
    public Iterable<KoreanTextMatch> matches(final String text, final int startIndex) {
        return new Iterable<KoreanTextMatch>() {
            public Iterator<KoreanTextMatch> iterator() {
                return new Iterator<KoreanTextMatch>() {
                    KoreanTextMatch _match = match(text, startIndex);

                    @Override
                    public boolean hasNext() {
                        return _match.success();
                    }

                    @Override
                    public KoreanTextMatch next() {
                        KoreanTextMatch result = _match;
                        _match = _match.nextMatch();
                        return result;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    private KoreanTextMatch match(String text, int startIndex, int length) {
        if (_pattern.length() == 0)
            return new KoreanTextMatch(this, text, 0, 0);

        for (int i = startIndex; i < startIndex + length - _pattern.length() + 1; i++) {
            for (int j = 0; j < _pattern.length(); j++) {
                if (!KoreanCharApproxMatcher.isMatch(text.charAt(i + j), _pattern.charAt(j)))
                    break;

                if (j == _pattern.length() - 1)
                    return new KoreanTextMatch(this, text, i, _pattern.length());
            }
        }
        return KoreanTextMatch.EMPTY;
    }

    /**
     * 주어진 {@code text} 내에 주어진 {@code pattern}이 존재하는지 여부를 조사한다.
     *
     * 정규식 앵커 {@code ^}와 {@code $}를 사용하여 {@code pattern}의 위치를
     * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return {@code text} 내에 {@code pattern}이 존재하면 {@code true},
     *         그렇지 않으면 {@code false}.
     * @throws IllegalArgumentException {@code text} 또는 {@code pattern}이
     *         {@code null}일 때.
     */
    public static boolean isMatch(String text, String pattern) {
        return match(text, pattern).success();
    }

    /**
     * 주어진 {@code text} 내에서 주어진 {@code pattern}의 첫번째 출현을 찾는다.
     *
     * 모든 출현을 찾으려면 {@link #matches(String, String)}를 사용한다.
     *
     * 정규식 앵커 {@code ^}와 {@code $}를 사용하여 {@code pattern}의 위치를
     * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return 검색 결과를 담은 {@link KoreanTextMatch} 인스턴스.
     *         {@link KoreanTextMatch#success()}가 {@code true}일 때만 유효하다.
     *         검색이 실패하면 {@link KoreanTextMatch#EMPTY}를 리턴한다.
     * @throws IllegalArgumentException {@code text} 또는 {@code pattern}이
     *         {@code null}일 때.
     */
    public static KoreanTextMatch match(String text, String pattern) {
        return new KoreanTextMatcher(pattern).match(text);
    }

    /**
     * 주어진 {@code text} 내에서 주어진 {@code pattern}의 모든 출현을 찾는다.
     *
     * 첫번째 출현만 찾으려면 {@link #match(String, String)}를 사용한다.
     *
     * 정규식 앵커 {@code ^}와 {@code $}를 사용하여 {@code pattern}의 위치를
     * 검색 대상 문자열의 시작과 끝으로 한정할 수 있다.
     *
     * @param text 검색 대상 문자열
     * @param pattern 검색할 패턴
     * @return 검색 결과를 담은 {@code Iterable<KoreanTextMatch>} 인스턴스.
     * @throws IllegalArgumentException {@code text} 또는 {@code pattern}이
     *         {@code null}일 때.
     */
    public static Iterable<KoreanTextMatch> matches(String text, String pattern) {
        return new KoreanTextMatcher(pattern).matches(text);
    }

    private String stripAnchors(String pattern) {
        if (!_foundStartAnchor && !_foundEndAnchor)
            return pattern;

        int startIndex = _foundStartAnchor ? 1 : 0;
        int length = pattern.length() - (_foundStartAnchor ? 1 : 0) - (_foundEndAnchor ? 1 : 0);
        return pattern.substring(startIndex, startIndex + length);
    }

    private long getTextRange(String text, int hintLength, int startIndex) {
        boolean trimStart = _foundEndAnchor, trimEnd = _foundStartAnchor;

        int length = text.length() - startIndex;

        if (length < hintLength)
            return -1;

        if (trimStart && trimEnd) {
            if (text.length() != hintLength)
                return -1;
        } else if (trimStart) {
            startIndex = text.length() - hintLength;
            length = hintLength;
        } else if (trimEnd) {
            if (startIndex != 0)
                return -1;
            length = hintLength;
        }
        return (long)startIndex << 32 | length;
    }
}
